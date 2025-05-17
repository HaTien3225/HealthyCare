import axios from "axios";
import cookie from 'react-cookies';

const BASE_URL = 'http://localhost:8080/ung_dung_dat_lich_kham_suc_khoe_truc_tuyen';

export const endpoints = {
    'register': '/api/register',
    'login': '/api/login',
    'current-user': '/api/profile',
    'doctorLichKham': (bacSiId) => `/api/doctor/lichkham?bacSiId=${bacSiId}`,
    'updateLichKham': (id) => `/api/doctor/lichkham/${id}`,
    'getHoSoSucKhoe': (benhNhanId) => `/api/doctor/hosobenhnhan/${benhNhanId}`,
    'updateHoSoSucKhoe': (benhNhanId) => `/api/doctor/hosobenhnhan/${benhNhanId}`,
    'doctorThongKe': (bacSiId) => `/api/doctor/thongke/${bacSiId}`,
    'doctorTuvan': (senderId, receiverId) => `/api/doctor/tuvan/${senderId}/${receiverId}`,
    'sendTuvan': "/api/doctor/tuvan",
    'lichkhampending': (bacSiId) => `/api/doctor/lichkham/pending?bacSiId=${bacSiId}`,
    'accept': (id) => `/api/doctor/lichkham/${id}/accept`,
    'reject': (id) => `/api/doctor/lichkham/${id}/reject`,
    'acceptedLichKham': (bacSiId) => `/api/doctor/lichkham/accepted?bacSiId=${bacSiId}`,
    'updateStatusLichKham': (id) => `/api/doctor/lichkham/${id}/update-status`,
     'donkham': (id) => `/api/doctor/lichkham/${id}/donkham`,
     'get_benh': (donKhamId) => `/api/doctor/donkham/${donKhamId}/benh`,
     'search_benh': (keyword) => `/api/doctor/benh/search?keyword=${keyword}`,
     'add_benh': (donKhamId, benhId) => `/api/doctor/donkham/${donKhamId}/benh/${benhId}`,
     'add_xet_nghiem': (donKhamId) => `/api/doctor/donkham/${donKhamId}/xetnghiem`,

    //user api
    "hososuckhoe": "/api/hososuckhoe",
    "donkham": "/api/donkham",
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
