import cookie from 'react-cookies'

export default (current, action) => {
    switch (action.type) {
        case "login":
            
            if (Array.isArray(action.payload)) {

                action.payload = action.payload.join('');
            }
            
            try {
                const parsedPayload = typeof action.payload === 'string' ? JSON.parse(action.payload) : action.payload;
                return parsedPayload;
            } catch (error) {
                console.error('Lỗi khi phân tích JSON:', error);
                return current;  
            }

        case "logout":
            localStorage.removeItem("token");
            localStorage.removeItem("user");
            cookie.remove('token');
            return null;

        default:
            return current;  
    }
}
