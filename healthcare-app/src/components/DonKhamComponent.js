import React, { useState } from "react";
import axios from "axios";

const DonKhamComponent = () => {
  const [lichKhamId, setLichKhamId] = useState("");
  const [donKham, setDonKham] = useState(null);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [benhList, setBenhList] = useState([]);
  const [selectedBenhId, setSelectedBenhId] = useState("");
  const [xetNghiem, setXetNghiem] = useState("");

  // 1. Tạo đơn khám mới
  const createDonKham = async () => {
    try {
      const response = await axios.post(`/api/doctor/lichkham/${lichKhamId}/donkham`, {});
      setDonKham(response.data);
      alert("Tạo đơn khám thành công!");
    } catch (error) {
      alert(error.response?.data || "Tạo đơn khám thất bại!");
    }
  };

  // 2. Tìm kiếm bệnh
  const searchBenh = async () => {
    try {
      const res = await axios.get(`/api/doctor/benh/search?keyword=${searchKeyword}`);
      setBenhList(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // 3. Gán bệnh vào đơn khám
  const assignBenh = async () => {
    try {
      await axios.post(`/api/doctor/donkham/${donKham.id}/benh/${selectedBenhId}`);
      alert("Đã gán bệnh vào đơn khám!");
    } catch (err) {
      alert("Lỗi khi gán bệnh!");
    }
  };

  // 4. Thêm xét nghiệm
  const addXetNghiem = async () => {
    try {
      await axios.post(`/api/doctor/donkham/${donKham.id}/xetnghiem`, {
        tenXetNghiem: xetNghiem
      });
      alert("Thêm xét nghiệm thành công!");
    } catch (err) {
      alert("Thêm xét nghiệm thất bại!");
    }
  };

  return (
    <div className="p-4 space-y-4">
      <div>
        <h2 className="text-xl font-bold">1. Tạo đơn khám</h2>
        <input
          type="number"
          placeholder="Nhập lịch khám ID"
          value={lichKhamId}
          onChange={(e) => setLichKhamId(e.target.value)}
          className="border px-2 py-1"
        />
        <button onClick={createDonKham} className="ml-2 bg-blue-500 text-white px-4 py-1 rounded">
          Tạo đơn khám
        </button>
      </div>

      {donKham && (
        <>
          <div>
            <h2 className="text-xl font-bold">2. Tìm và gán bệnh</h2>
            <input
              type="text"
              placeholder="Tìm tên bệnh"
              value={searchKeyword}
              onChange={(e) => setSearchKeyword(e.target.value)}
              className="border px-2 py-1"
            />
            <button onClick={searchBenh} className="ml-2 bg-green-500 text-white px-4 py-1 rounded">
              Tìm kiếm
            </button>

            <select
              value={selectedBenhId}
              onChange={(e) => setSelectedBenhId(e.target.value)}
              className="block mt-2 border px-2 py-1"
            >
              <option value="">Chọn bệnh</option>
              {benhList.map((benh) => (
                <option key={benh.id} value={benh.id}>
                  {benh.tenBenh}
                </option>
              ))}
            </select>
            <button onClick={assignBenh} className="mt-2 bg-purple-500 text-white px-4 py-1 rounded">
              Gán bệnh
            </button>
          </div>

          <div>
            <h2 className="text-xl font-bold">3. Thêm xét nghiệm</h2>
            <input
              type="text"
              placeholder="Tên xét nghiệm"
              value={xetNghiem}
              onChange={(e) => setXetNghiem(e.target.value)}
              className="border px-2 py-1"
            />
            <button onClick={addXetNghiem} className="ml-2 bg-red-500 text-white px-4 py-1 rounded">
              Thêm xét nghiệm
            </button>
          </div>
        </>
      )}
    </div>
  );
};

export default DonKhamComponent;
