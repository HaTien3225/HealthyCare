import { useEffect, useState, useRef, useContext } from "react";
import { collection, addDoc, serverTimestamp, query, orderBy, onSnapshot, doc, setDoc } from "firebase/firestore";
import { auth, db } from "../configs/firebase";
import { useParams } from "react-router-dom";
import { MyUserContext } from "../configs/Contexts";
import { authApis, endpoints } from "../configs/Apis";

const ChatComponent = () => {
    const user = useContext(MyUserContext);
    const { id: otherUserId } = useParams();

    const currentUserId = user.id;
    const chatId = [currentUserId, otherUserId].sort().join("_");

    const [messages, setMessages] = useState([]);
    const [text, setText] = useState("");
    const [file, setFile] = useState("");
    const [otherTyping, setOtherTyping] = useState(false);
    const messagesEndRef = useRef(null);
    const typingTimeoutRef = useRef(null);
    const fileInputRef = useRef(null); // Ref để reset input file

    useEffect(() => {
        const q = query(
            collection(db, "chats", chatId, "messages"),
            orderBy("timestamp", "asc")
        );
        const unsubscribe = onSnapshot(q, (snapshot) => {
            setMessages(snapshot.docs.map(doc => ({ id: doc.id, ...doc.data() })));
            scrollToBottom();
        });
        return unsubscribe;
    }, [chatId]);

    useEffect(() => {
        const typingRef = doc(db, "typingStatus", chatId);
        const unsubscribe = onSnapshot(typingRef, (docSnap) => {
            const data = docSnap.data();
            if (data && data.typingUserId !== currentUserId) {
                setOtherTyping(data.isTyping);
            } else {
                setOtherTyping(false);
            }
        });
        return unsubscribe;
    }, [chatId, currentUserId]);

    const updateTypingStatus = async (isTyping) => {
        if (!currentUserId) return;
        const typingRef = doc(db, "typingStatus", chatId);
        await setDoc(typingRef, {
            isTyping,
            typingUserId: currentUserId,
            timestamp: Date.now(),
        });
    };

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    };

    const handleSend = async () => {
        if (!text.trim() && !file) return;

        let fileUrl = null;

        if (file) {
            try {
                const formData = new FormData();
                formData.append("file", file);

                const response = await authApis().post(endpoints.uploads, formData, {
                    headers: {
                        "Content-Type": "multipart/form-data",
                    },
                });

                const data = await response.data;

                if (data.error) {
                    alert("Không thể tải file lên Cloudinary. Vui lòng thử lại.");
                    return;
                }

                fileUrl = data.secure_url;
            } catch (err) {
                console.error("Lỗi upload file:", err);
                alert("Không thể tải file lên. Vui lòng thử lại.");
                return;
            }
        }

        await addDoc(collection(db, "chats", chatId, "messages"), {
            senderId: currentUserId,
            text: text.trim(),
            fileUrl,
            timestamp: serverTimestamp(),
        });

        setText("");
        setFile(null);
        if (fileInputRef.current) fileInputRef.current.value = null; // Reset input file
        updateTypingStatus(false);
    };

    const handleTyping = (e) => {
        setText(e.target.value);
        updateTypingStatus(true);
        if (typingTimeoutRef.current) clearTimeout(typingTimeoutRef.current);
        typingTimeoutRef.current = setTimeout(() => updateTypingStatus(false), 3000);
    };

    return (
        <div style={{ maxWidth: 600, margin: "auto" }}>
            <div
                style={{
                    maxHeight: "300px",
                    overflowY: "auto",
                    border: "1px solid #ccc",
                    padding: 10,
                    marginBottom: 10,
                    backgroundColor: "#f9f9f9",
                }}
            >
                {messages.map((msg) => (
                    <div
                        key={msg.id}
                        style={{
                            marginBottom: 10,
                            textAlign: msg.senderId === currentUserId ? "right" : "left",
                        }}
                    >
                        <strong>{msg.senderId === currentUserId ? "Bạn" : "Đối phương"}:</strong>{" "}
                        {msg.text && <span>{msg.text}</span>}
                        {msg.fileUrl && (
                            <div style={{ marginTop: 5 }}>
                                {msg.fileUrl.match(/\.(jpeg|jpg|png|gif)$/i) ? (
                                    <a href={msg.fileUrl} download target="_blank" rel="noopener noreferrer">
                                        <img src={msg.fileUrl} alt="Gửi ảnh" style={{ maxWidth: 200 }} />
                                    </a>
                                ) : (
                                    <a href={msg.fileUrl} download target="_blank" rel="noopener noreferrer">
                                        📎 Tải file đính kèm
                                    </a>
                                )}
                            </div>
                        )}
                    </div>
                ))}
                {otherTyping && <div><em>Đối phương đang nhập...</em></div>}
                <div ref={messagesEndRef} />
            </div>

            <input
                type="text"
                value={text}
                onChange={handleTyping}
                placeholder="Nhập tin nhắn... 😊"
                style={{ width: "70%", padding: 8 }}
            />
            <input
                type="file"
                ref={fileInputRef}
                accept="image/*"
                onChange={(e) => setFile(e.target.files[0])}
                style={{ width: "20%" }}
            />
            <button onClick={handleSend} style={{ width: "10%" }}>
                Gửi
            </button>
        </div>
    );
};

export default ChatComponent;
