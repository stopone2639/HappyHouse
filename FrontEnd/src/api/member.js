import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api
    .post(`/userapi/login`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api
    .get(`/userapi/myInfo/${userid}`)
    .then(success)
    .catch(fail);
}

// function logout(success, fail)
//회원 가입
async function registMember(user, success, fail) {
  api
    .post("/userapi/regist", JSON.stringify(user))
    .then(success)
    .catch(fail);
}
//아이디 찾기
async function idFindMember(user, success, fail) {
  api
    .post("/userapi/idFind", JSON.stringify(user))
    .then(success)
    .catch(fail);
}
// 비밀 번호 찾기
async function pwFindMember(user, success, fail) {
  api
    .post("/userapi/pwFind", JSON.stringify(user))
    .then(success)
    .catch(fail);
}
//회원 탈퇴
async function deleteMember(user, success, fail) {
  api
    .post("/userapi/delete", JSON.stringify(user))
    .then(success)
    .catch(fail);
}
export {
  login,
  findById,
  registMember,
  idFindMember,
  pwFindMember,
  deleteMember,
};
