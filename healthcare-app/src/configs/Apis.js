import axios from "axios";
import cookie from 'react-cookies';

const BASE_URL = 'http://localhost:8080/ung_dung_dat_lich_kham_suc_khoe_truc_tuyen/api'; //thanh toán online xài cloudflare tunnel thì sẽ thế tên miền ra internet chứ ko localhost
// const BASE_URL = 'https://expensive-pending-french-span.trycloudflare.com/ung_dung_dat_lich_kham_suc_khoe_truc_tuyen/api';
export const endpoints = {
    'register': '/register',
    'login': '/login',
    'current-user': '/profile',
    'getHoSoSucKhoe': (benhNhanId) => `/doctor/hosobenhnhan/${benhNhanId}`,
    'updateHoSoSucKhoe': (benhNhanId) => `/doctor/hosobenhnhan/${benhNhanId}`,
    'doctorThongKe': `/doctor/thongke`,
    'doctorTuvan': (senderId, receiverId) => `/doctor/tuvan/${senderId}/${receiverId}`,
    'sendTuvan': "/doctor/tuvan",
    'lichkhampending': `/doctor/lichkham/pending`,
    'accept': (id) => `/doctor/lichkham/${id}/accept`,
    'reject': (id) => `/doctor/lichkham/${id}/reject`,
    'acceptedLichKham': `/doctor/lichkham/accepted`,
    'updateStatusLichKham': (id) => `/doctor/lichkham/${id}/update-status`,
    'donkham': (id) => `/doctor/lichkham/${id}/donkham`,
    'get_benh': (donKhamId) => `/doctor/donkham/${donKhamId}/benh`,
    'search_benh': (keyword) => `/doctor/benh/search?keyword=${keyword}`,
    'add_benh': (donKhamId, benhId) => `/doctor/donkham/${donKhamId}/benh/${benhId}`,
    'add_xet_nghiem': (donKhamId) => `/doctor/donkham/${donKhamId}/xetnghiem`,
    'benhPhoBien': (month, quarter) => {
        const params = [];
        if (month) params.push(`month=${month}`);
        if (quarter) params.push(`quarter=${quarter}`);
        return `/doctor/benhphobien${params.length ? '?' + params.join('&') : ''}`;
    },
    'send_mail': (lichKhamId) => `/doctor/lichkham/${lichKhamId}/send-invite`,
    'google': '/firebase-login',
    'benhdoctor':'/doctor/benh',
    'uploads': "/upload-image",
    'khambenh':"/doctor/donkham",
    'chitietdonkhamdoctor': '/doctor/chitietdonkham',
    'xetnghiemdoctor': '/doctor/xetnghiem',
    'upload': "/doctor/upload-license",
    "danhgiadoctor":"/doctor/danhgia",
     "danhgiareply": (id) => `/doctor/danhgia/${id}/reply`,
    "donkhamview": "/doctor/viewdonkham",
    "xetnghiemview":"/doctor/viewxetnghiem",
    "chitietdonkhamview":"/doctor/viewchitietdonkham",
    

    //user api
    "hososuckhoe": "/hososuckhoe",
    "donkhamuser": "/donkham",
    "chitietdonkham": "/chitietdonkham",
    "xetnghiem": "/xetnghiem",
    "thanhtoan": "/thanhtoan",
    "lichkhamuser": "/lichkham",
    "bennvienuser":"/benhvien",
    "khoauser":"/benhvien/khoa",
    "bacsiuser":"/bacsi",
    "khunggio":"/khunggio",
    "khunggiotrong":"/khunggiotrong",
    "danhgiauser":"/danhgia",
    "assitant":"/ai/assitant",
}

export const authApis = () => {
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': `Bearer ${cookie.load('token')}`
        }
    });
};

export default axios.create({
    baseURL: BASE_URL
})
