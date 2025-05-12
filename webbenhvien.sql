drop database if exists webbenhvien;

create database webbenhvien;

use webbenhvien;

create table Role(
	id int not null auto_increment primary key,
    role varchar(255) not null
);


create table Benhvien(
	id int not null auto_increment primary key,
    ngay_thanh_lap date not null,
    ten_benh_vien varchar(255)  CHARACTER SET utf8 COLLATE utf8_unicode_ci not null,
    gioi_thieu varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci not null,
    dia_chi varchar(255) not null
);

create table Khoa(
	id int not null auto_increment primary key,
    created_date date not null,
    ten_khoa varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci not null,
    mo_ta varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci not null,
    benh_vien_id int not null,
    foreign key (benh_vien_id) references Benhvien(id) on delete restrict
);

create table Benh(
	id int not null auto_increment primary key,
    created_date date not null,
    ten_benh varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci not null,
    mo_ta varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci,
    khoa_id int not null,
    foreign key (khoa_id) references Khoa(id) on delete cascade
);

create table User (
	id int not null auto_increment primary key,
    ho varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci not null,
    ten varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci not null,
    username varchar(20) not null unique,
    password varchar(255) not null,
    email varchar(225) not null,
    created_date date not null,
    cccd varchar(12) not null,
    phone varchar(10) not null,
    is_active boolean default false,
    avatar varchar(2083) CHARACTER SET utf8 COLLATE utf8_unicode_ci,
    
    khoa_id int,
    foreign key (khoa_id) references Khoa(id) on delete restrict,
    role_id int not null,
    foreign key (role_id) references Role(id) on delete restrict
);

create table Giayphephanhnghe(
	id int not null auto_increment primary key,
    created_date date not null,
    image varchar(2083)  CHARACTER SET utf8 COLLATE utf8_unicode_ci not null,
    is_valid boolean not null default false,
    bac_si_id int not null,
    foreign key (bac_si_id) references User(id)
);

create table HoSoSucKhoe(
	id int not null auto_increment primary key,
	chieu_cao int,
    can_nang int,
    birth date,
    
    benh_nhan_id int not null unique,
    foreign key (benh_nhan_id) references User(id) on delete restrict
);

create table Khunggio(
	id int not null auto_increment primary key,
    ten_kg varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci not null,
    gio_bat_dau time not null,
    gio_ket_thuc time not null
);

create table Lichkham(
	id int not null auto_increment primary key,
    ngay datetime not null,
    da_kham boolean default false,
    
    bac_si_id int not null,
    foreign key (bac_si_id) references User(id) on delete restrict,
    khunggio_id int not null,
    foreign key (khunggio_id) references Khunggio(id) on delete restrict
);

create table Tinnhan(
	id INT AUTO_INCREMENT PRIMARY KEY,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    message TEXT DEFAULT NULL,  
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES User(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES User(id) ON DELETE CASCADE    
);

create table Donkham(
	id int not null auto_increment primary key,
    ghi_chu varchar(2048) CHARACTER SET utf8 COLLATE utf8_unicode_ci,
    benh_id int not null,
    hssk_id int not null,
    bac_si_id int not null,
    foreign key (benh_id) references Benh(id),
    foreign key (hssk_id) references HoSoSucKhoe(id),
    foreign key (bac_si_id) references User(id)
);

create table Chitietdonkham(
	id int not null auto_increment primary key,
    dich_vu varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci not null,
    gia_tien decimal(10,2),
    
    don_kham_id int not null,
    foreign key (don_kham_id) references Donkham(id) on delete restrict
);

create table Xetnghiem(
	id int not null auto_increment primary key,
    
    don_kham_id int not null,
    foreign key (don_kham_id) references Donkham(id) on delete restrict
);

create table Chitietxetnghiem(
	id int not null auto_increment primary key,
    mo_ta varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci not null,
    xet_nghiem_id int not null,
    foreign key (xet_nghiem_id) references Xetnghiem(id) on delete restrict
);

create table Danhgia(
	id int not null auto_increment primary key,
    rating decimal(1,0) not null,
    binh_luan varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci,
    phan_hoi varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci,
    
    benh_nhan_id int not null ,
    foreign key (benh_nhan_id) references User(id) on delete restrict,
    bac_si_id int not null,
    foreign key (bac_si_id) references User(id) on delete restrict
);

INSERT INTO Role(role) VALUES ('ROLE_ADMIN'), ('ROLE_USER'), ('ROLE_DOCTOR');

-- admin123
INSERT INTO User (
    id, ho, ten, username, password, email, created_date, 
    cccd, phone, is_active, avatar, khoa_id, role_id
) VALUES (
    1, 'Nguyen Van', 'Admin', 'admin', 
    '$2a$10$soTjFAQani8fVvQ/LLhcF.y6xv2DH/.zbgLP4sigZg1D4y6x1F3ie', 
    'admin@example.com', 
    CURDATE(), 
    '012345678901', 
    '0987654321', 
    true, 
    NULL, 
    NULL, 
    1 -- role_id = 1 (ROLE_ADMIN)
);

--------------------------------------------------- 


-- use webbenhvien;

INSERT INTO Benhvien (ngay_thanh_lap, ten_benh_vien, gioi_thieu, dia_chi)
VALUES 
('1975-01-01', 'Bệnh viện Chợ Rẫy', 'Bệnh viện đa khoa trung ương lớn nhất miền Nam', '201B Nguyễn Chí Thanh, Quận 5, TP.HCM'),

('1956-09-23', 'Bệnh viện Bạch Mai', 'Bệnh viện tuyến cuối về nội khoa', '78 Giải Phóng, Đống Đa, Hà Nội'),

('2000-06-15', 'Bệnh viện Đại học Y Dược TP.HCM', 'Trung tâm y tế chất lượng cao', '215 Hồng Bàng, Quận 5, TP.HCM'),

('1989-04-10', 'Bệnh viện Hữu nghị Việt Đức', 'Chuyên khoa ngoại khoa hàng đầu', '40 Tràng Thi, Hoàn Kiếm, Hà Nội'),

('1990-07-20', 'Bệnh viện 108', 'Bệnh viện quân đội trung ương', '1 Trần Hưng Đạo, Hai Bà Trưng, Hà Nội');


-- Insert mẫu vào bảng Khoa cho benh_vien_id = 1
INSERT INTO Khoa (created_date, ten_khoa, mo_ta, benh_vien_id)
VALUES 
('2025-04-28', 'Khoa Nội', 'Khoa chuyên điều trị các bệnh về nội khoa', 1),
('2025-04-28', 'Khoa Ngoại', 'Khoa chuyên điều trị các bệnh về ngoại khoa', 1),
('2025-04-28', 'Khoa Nhi', 'Khoa chuyên điều trị các bệnh cho trẻ em', 1);

-- Insert mẫu vào bảng Khoa cho benh_vien_id = 2
INSERT INTO Khoa (created_date, ten_khoa, mo_ta, benh_vien_id)
VALUES 
('2025-04-28', 'Khoa Da liễu', 'Khoa chuyên điều trị các bệnh về da', 2),
('2025-04-28', 'Khoa Mắt', 'Khoa chuyên điều trị các bệnh về mắt', 2),
('2025-04-28', 'Khoa Tai mũi họng', 'Khoa chuyên điều trị các bệnh về tai mũi họng', 2);

INSERT INTO Benh (created_date, ten_benh, mo_ta, khoa_id) VALUES
('2025-05-05', 'Viêm phổi', 'Bệnh viêm phổi cấp tính', 1),
('2025-05-05', 'Tiểu đường', 'Bệnh rối loạn chuyển hóa đường', 1),
('2025-05-05', 'Viêm gan B', 'Bệnh lây qua đường máu và tình dục', 1),
('2025-05-05', 'Viêm da cơ địa', 'Bệnh viêm da mãn tính gây ngứa', 4),
('2025-05-05', 'Nấm da', 'Bệnh nhiễm nấm trên bề mặt da', 4),
('2025-05-05', 'Chàm (eczema)', 'Bệnh da liễu gây nổi mẩn đỏ và ngứa', 4);

INSERT INTO Khunggio (ten_kg, gio_bat_dau, gio_ket_thuc)
VALUES 
('07:00 - 08:00', '07:00:00', '08:00:00'),
('08:00 - 09:00', '08:00:00', '09:00:00'),
('09:00 - 10:00', '09:00:00', '10:00:00'),
('10:00 - 11:00', '10:00:00', '11:00:00'),
('11:00 - 12:00', '11:00:00', '12:00:00'),
('13:00 - 14:00', '13:00:00', '14:00:00'),
('14:00 - 15:00', '14:00:00', '15:00:00'),
('15:00 - 16:00', '15:00:00', '16:00:00'),
('16:00 - 17:00', '16:00:00', '17:00:00'),
('17:00 - 18:00', '17:00:00', '18:00:00');

INSERT INTO Giayphephanhnghe (created_date, image, is_valid, bac_si_id)
VALUES 
(CURDATE(), 'https://via.placeholder.com/300x400?text=Giay+Phep+1', true, 4),
(CURDATE(), 'https://via.placeholder.com/300x400?text=Giay+Phep+2', true, 6);
