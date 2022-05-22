import axios from "axios";
import { API_BASE_URL } from "@/config";

// axios 객체 생성
function apiInstance() {
  const token = sessionStorage.getItem("token");
  console.log(token);
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  });
  return instance;
}

// function houseInstance() {
//   const instance = axios.create({
//     baseURL: APT_DEAL_URL,
//     headers: {
//       "Content-type": "application/json",
//     },
//   });
//   return instance;
// }

export { apiInstance };
