import axios from "axios";
import cookie from 'react-cookies';

const BASE_URL = 'http://localhost:8080/ung_dung_dat_lich_kham_suc_khoe_truc_tuyen';

export const endpoints = {
    'register': '/api/register',
    'login': '/api/login',
    'current-user': '/api/secure/profile',
    'doctorLichKham': (bacSiId) => `/doctor/api/lichkham?bacSiId=${bacSiId}`,
    'updateLichKham': (id) => `/doctor/api/lichkham/${id}`,
    'getHoSoSucKhoe': (benhNhanId) => `/doctor/api/hosobenhnhan/${benhNhanId}`,
    'updateHoSoSucKhoe': (benhNhanId) => `/doctor/api/hosobenhnhan/${benhNhanId}`,
    'doctorThongKe': (bacSiId) => `/doctor/api/thongke/${bacSiId}`,
    'doctorTuvan': (senderId, receiverId) => `/doctor/api/tuvan/${senderId}/${receiverId}`,
    'sendTuvan': "/doctor/api/doctor/tuvan",
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
});
