drop database if exists webbenhvien;

create database webbenhvien;

use webbenhvien;

create table Role(
	id int not null auto_increment primary key,
    role varchar(255) not null
);

create table Giayphephanhnghe(
	id int not null auto_increment primary key,
    created_date date not null,
    image varchar(2083)  CHARACTER SET utf8 COLLATE utf8_unicode_ci not null,
    is_valid boolean not null default false
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
    foreign key (benh_vien_id) references Benhvien(id) on delete cascade
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
    username varchar(20) not null,
    password varchar(255) not null,
    email varchar(225) not null,
    created_date date not null,
    cccd varchar(12) not null,
    phone varchar(10) not null,
    is_active boolean default false,
    avatar varchar(2083) CHARACTER SET utf8 COLLATE utf8_unicode_ci,
    
    khoa_id int,
    foreign key (khoa_id) references Khoa(id) on delete restrict,
    giayphep_id int unique,
    foreign key (giayphep_id) references Giayphephanhnghe(id) on delete restrict,
    role_id int not null,
    foreign key (role_id) references Role(id) on delete restrict
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
    
    benh_nhan_id int not null unique,
    foreign key (benh_nhan_id) references User(id) on delete restrict,
    bac_si_id int not null,
    foreign key (bac_si_id) references User(id) on delete restrict
);
