<template>
  <div class="container">
    <div id="box" class="col-md-12 text-center">
      <h2>회원 가입</h2>
      <p slot="description" class="description">
        happy house에 오신 걸 환영 합니다.
      </p>
      <md-field class="md-form-group  " slot="inputs">
        <md-icon><b-icon icon="pencil"></b-icon></md-icon>
        <label>이름</label>
        <md-input v-model="username" ref="userpw"></md-input>
      </md-field>
      <md-field
        class="md-form-group"
        :class="[{ 'md-error': !checkId }]"
        slot="inputs"
      >
        <md-icon><b-icon icon="person-circle"></b-icon></md-icon>
        <label>아이디</label>
        <md-input v-model="userid" @change="idvalueCheck" ref="id"></md-input>
        <b-icon
          icon="exclamation-circle-fill"
          variant="danger"
          v-if="!checkId"
          v-b-tooltip.hover.right="'사용이 불가능한 아이디입니다.'"
        ></b-icon>
      </md-field>
      <md-field class="md-form-group dis" slot="inputs">
        <md-icon><b-icon icon="lock"></b-icon></md-icon>
        <label>비밀번호</label>
        <md-input type="password" v-model="userpw" ref="userpw"></md-input>
      </md-field>
      <md-field class="md-form-group " slot="inputs">
        <md-icon><b-icon icon="mailbox"></b-icon></md-icon>
        <label>이메일</label>
        <md-input type="email" v-model="useraddress" ref="usermail"></md-input>
      </md-field>
      <md-field class="md-form-group " slot="inputs">
        <md-icon><b-icon icon="telephone-fill"></b-icon></md-icon>
        <label>전화번호</label>
        <md-input v-model="usernumber" ref="userphone"></md-input>
      </md-field>
      <!-- 
      <md-button slot="footer" class="md-info" v-on:click="idvalueCheck()">
        아이디 중복 체크
      </md-button> -->

      <md-button slot="footer" class="md-success" v-on:click="registCheck()">
        회원 가입
      </md-button>

      <md-button slot="footer" class="md-warning" v-on:click="initData()">
        초기화
      </md-button>
    </div>
  </div>
</template>

<script>
import { registMember, idCheck } from "@/api/member";
export default {
  data() {
    return {
      username: "",
      userid: "",
      userpw: "",
      useraddress: "",
      usernumber: "",
      checkId: true,
    };
  },
  props: {
    header: {
      type: String,
      default: require("@/assets/img/house-bg.png"),
    },
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.header})`,
      };
    },
  },
  methods: {
    // falseCkid() {
    //   this.checkId = false;
    // },
    idvalueCheck() {
      idCheck(
        this.userid,
        ({ data }) => {
          if (data === "success") {
            this.checkId = true;
            // alert(this.userid + "는 사용 가능한 아이디 입니다.");
          } else {
            this.checkId = false;
            // alert(this.userid + "는 사용 불가능한 아이디 입니다.");
          }
        },
        (error) => {
          console.log(error);
          // eslint-disable-next-line prettier/prettier
        }
      );
    },
    initData() {
      this.username = "";
      this.userid = "";
      this.userpw = "";
      this.useraddress = "";
      this.usernumber = "";
      this.checkId = true;
    },
    registCheck() {
      let err = false;
      let msg = "";

      if (!this.username) {
        msg = "이름을 입력해주세요.";
        err = true;
      } else if (!this.userid) {
        msg = "아이디를 입력해주세요.";
        err = true;
      } else if (!this.userpw) {
        msg = "비밀번호를 입력해주세요.";
        err = true;
      } else if (!this.useraddress) {
        msg = "이메일을 입력해주세요.";
        err = true;
      } else if (!this.usernumber) {
        msg = "전화번호를 입력해주세요.";
        err = true;
      } else if (!this.checkId) {
        msg = "아이디 중복 체크를 진행해주세요.";
        err = true;
      }
      if (err) {
        alert(msg);
      } else {
        this.regist();
      }
    },
    regist() {
      registMember(
        {
          username: this.username,
          userid: this.userid,
          userpw: this.userpw,
          useraddress: this.useraddress,
          usernumber: this.usernumber,
        },
        ({ data }) => {
          let msg = "회원 가입 시 문제가 발생했습니다.";
          let err = true;
          if (data === "success") {
            msg = "회원 가입이 완료되었습니다.";
            err = false;
          }
          alert(msg);
          if (!err) {
            this.checkId = false;
            this.$router.push({ name: "login" });
          } else {
            this.$router.push({ name: "regist" });
          }
        },
        (error) => {
          let msg = "서버에 문제가 발생했습니다.";
          console.log(error);
          alert(msg);
          // eslint-disable-next-line prettier/prettier
        }
      );
    },
  },
};
</script>

<style>
#box {
  padding: 100px;
  margin: 5px;
}
.md-form-group {
  width: 50%;
  margin: 1rem auto !important;
  padding: 15px;
}
/* .dis .md-icon-button {
  display: none !important;
} */
</style>
