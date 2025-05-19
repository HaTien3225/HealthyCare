import { useEffect, useState, useRef, useContext } from "react";
import { collection, addDoc, serverTimestamp, query, orderBy, onSnapshot, doc, setDoc } from "firebase/firestore";
import { ref, uploadBytes, getDownloadURL } from "firebase/storage";
import { db, storage } from "../configs/firebase";
import { useParams } from "react-router-dom";
import { MyUserContext } from "../configs/Contexts";

const ChatComponent = () => {
    const user = useContext(MyUserContext);
    const { id: otherUserId } = useParams();
    const [messages, setMessages] = useState([]);
    const [text, setText] = useState("");
    const [file, setFile] = useState(null);
    const [otherTyping, setOtherTyping] = useState(false);
    const messagesEndRef = useRef(null);

    const currentUserId = user.id;

   
    const chatId = [currentUserId, otherUserId].sort().join("_");

    useEffect(() => {
        const q = query(
            collection(db, "chats", chatId, "messages"),
            orderBy("timestamp", "asc")
        );
        const unsubscribe = onSnapshot(q, (snapshot) => {
            setMessages(snapshot.docs.map((doc) => ({ id: doc.id, ...doc.data() })));
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
                const storageRef = ref(storage, `chat_uploads/${Date.now()}_${file.name}`);
                const uploadResult = await uploadBytes(storageRef, file);
                fileUrl = await getDownloadURL(uploadResult.ref);
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
        updateTypingStatus(false);
    };

    useEffect(() => {
        if (Notification.permission !== "granted") {
            Notification.requestPermission();
        }
    }, []);

    useEffect(() => {
        if (document.hidden && messages.length > 0) {
            const lastMsg = messages[messages.length - 1];
            if (lastMsg.senderId !== currentUserId && Notification.permission === "granted") {
                new Notification("Tin nhắn mới", {
                    body: lastMsg.text || "📎 Tệp đính kèm",
                });
            }
        }
    }, [messages, currentUserId]);

    let typingTimeout;
    const handleTyping = (e) => {
        setText(e.target.value);
        updateTypingStatus(true);
        clearTimeout(typingTimeout);
        typingTimeout = setTimeout(() => updateTypingStatus(false), 3000);
    };
     if (!user) {
        return <div>Đang tải người dùng...</div>;
    }
    if (!otherUserId) {
        return <div>Không có người dùng để chat</div>;
    }


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
                                    <img src={msg.fileUrl} alt="Gửi ảnh" style={{ maxWidth: 200 }} />
                                ) : (
                                    <a href={msg.fileUrl} target="_blank" rel="noopener noreferrer">
                                        📎 File đính kèm
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
