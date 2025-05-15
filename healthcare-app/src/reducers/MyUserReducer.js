import cookie from 'react-cookies'

export default (current, action) => {
    switch (action.type) {
        case "login":
            // Kiểm tra nếu payload là một mảng các ký tự thay vì một chuỗi JSON hợp lệ
            if (Array.isArray(action.payload)) {
                // Chuyển mảng ký tự thành chuỗi JSON
                action.payload = action.payload.join('');
            }
            // Kiểm tra và phân tích chuỗi JSON
            try {
                const parsedPayload = typeof action.payload === 'string' ? JSON.parse(action.payload) : action.payload;
                return parsedPayload;  // Trả về đối tượng người dùng đã phân tích
            } catch (error) {
                console.error('Lỗi khi phân tích JSON:', error);
                return current;  // Nếu có lỗi, không thay đổi state
            }

        case "logout":
            cookie.remove('token');
            return null;

        default:
            return current;  // Trả về trạng thái hiện tại nếu không có action hợp lệ
    }
}
