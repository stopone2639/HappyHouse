<template>
  <b-row class="my-4 wc mx-auto d-flex align-items-center">
    <b-col class="sm-3">
      <b-form-select
        v-model="currentSido"
        :options="sidos"
        @change="getGugunList"
      >
      </b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-select
        v-model="currentGugun"
        :options="guguns"
        @change="getDongList"
      >
      </b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-select
        v-model="currentDong"
        :options="dongs"
        @change="getHouseList"
      >
      </b-form-select>
    </b-col>
    <b-button type="button" variant="inline-warning" @click="addLikeDong">
      <b-icon
        v-if="isLike"
        icon="star-fill"
        variant="warning"
        scale="1.5"
      ></b-icon>
      <b-icon v-else icon="star" variant="warning" scale="1.5"></b-icon>
    </b-button>
  </b-row>
</template>

<script>
import { sidoList, gugunList, dongList, houseListByDong } from "@/api/house.js";
import { registLikeDong, deleteLikeDong } from "@/api/member.js";
import { mapActions, mapState } from "vuex";

const memberStore = "memberStore";
const houseStore = "houseStore";
export default {
  name: "SearchDong",
  data() {
    return {
      currentSido: null,
      currentGugun: null,
      currentDong: null,
      sidos: [{ value: null, text: "선택" }],
      guguns: [{ value: null, text: "선택" }],
      dongs: [{ value: null, text: "선택" }],
      isLike: false,
    };
  },
  created() {
    this.currentSido = this.sidoCode;
    this.currentGugun = this.gugunCode;
    this.currentDong = this.dongCode;
    this.getSidoList();
    let params = this.$route.params;
    if (params.sidoCodeParam) {
      this.currentSido = params.sidoCodeParam.substr(0, 2); //시도 설정
      //구군 불러오기
      this.getGugunList();
      this.currentGugun = params.gugunCodeParam.substr(0, 5); //구군 설정
      //동 불러오기
      this.getDongList();
      this.currentDong = params.dongCodeParam; //동 설정
      this.getHouseList();
    }
  },
  destroyed() {
    this.clearSidoCode();
    this.clearGugunCode();
    this.clearDongCode();
  },

  computed: {
    ...mapState(memberStore, ["userInfo", "likeList"]),
    ...mapState(houseStore, ["sidoCode", "gugunCode", "dongCode"]),
  },
  methods: {
    ...mapActions(houseStore, [
      "setHouses",
      "setHousesFilter",
      "setSidoCode",
      "setGugunCode",
      "setDongCode",
      "clearSidoCode",
      "clearGugunCode",
      "clearDongCode",
    ]),
    ...mapActions(memberStore, ["setLikeList"]),
    getSidoList() {
      sidoList((res) => {
        res.data.forEach((sido) => {
          this.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
        });
      });
    },
    getGugunList() {
      this.setSidoCode(this.currentSido);
      this.guguns = [{ value: null, text: "선택" }];
      gugunList({ sidoCode: this.currentSido }, (res) => {
        res.data.forEach((gugun) => {
          if (gugun.gugunName != null) {
            if (!gugun.gugunCode) gugun.gugunCode = null;
            this.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
          }
        });
      });
    },
    getDongList() {
      this.setGugunCode(this.currentGugun);
      this.dongs = [{ value: null, text: "선택" }];
      dongList({ sidoCode: this.currentGugun }, (res) => {
        res.data.forEach((dong) => {
          if (dong.dongName != null) {
            if (!dong.dongCode) dong.dongCode = null;
            this.dongs.push({ value: dong.dongCode, text: dong.dongName });
          }
        });
      });
    },
    getHouseList() {
      this.setDongCode(this.currentDong);
      this.isLikeDong();
      houseListByDong(
        {
          dongCode: this.currentDong,
        },
        (res) => {
          this.setHouses(res.data);
          this.setHousesFilter(res.data);
          // eslint-disable-next-line prettier/prettier
        }
      );
    },

    async addLikeDong() {
      //로그인 확인
      if (!this.userInfo) {
        alert("로그인 후 가능한 서비스입니다.");
        this.$router.push({ name: "login" });
        return;
      }
      //관심지역으로 등록하기
      if (!this.dongCode) {
        alert("지역을 선택해주세요");
      } else {
        if (this.isLike) {
          //관심지역 해제
          await deleteLikeDong(
            {
              dongCode: this.dongCode,
              userid: this.userInfo.userid,
            },
            ({ data }) => {
              if (data == "success") {
                this.isLike = false;
                //vuex 리스트 다시 조회
                this.setLikeList(this.userInfo.userid);
              } else {
                alert("관심지역 해제에 실패하였습니다.");
              }
              // eslint-disable-next-line prettier/prettier
            }
          );
        } else {
          //관심지역 등록
          registLikeDong(
            {
              dongCode: this.dongCode,
              userid: this.userInfo.userid,
            },
            ({ data }) => {
              if (data === "success") {
                this.isLike = true;
                //vuex 리스트 다시 조회
                this.setLikeList(this.userInfo.userid);
              } else {
                alert("관심지역 등록에 실패하였습니다.");
              }
              // eslint-disable-next-line prettier/prettier
            }
          );
        }
      }
    },
    //현재 선택된 지역이 관심지역인지 확인
    isLikeDong() {
      this.isLike = false;
      this.likeList.map((dong) => {
        if (dong.dongCode == this.dongCode) {
          this.isLike = true;
        }
      });
    },
  },
};
</script>

<style>
.wc {
  width: 38rem;
  display: flex;
  align-content: center;
}
</style>
