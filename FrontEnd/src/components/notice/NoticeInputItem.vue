<template>
  <b-row class="mb-5">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group id="userid-group" label="작성자:" label-for="userid">
          <b-form-input
            :disabled="isUserid"
            v-model="userInfo.userid"
            type="text"
            :readonly="true"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="subject-group"
          label="제목:"
          label-for="subject"
          description="제목을 입력하세요."
        >
          <b-form-input
            id="subject"
            v-model="article.subject"
            type="text"
            required
            placeholder="제목 입력..."
          ></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label="내용:" label-for="content">
          <b-form-textarea
            id="content"
            v-model="article.content"
            placeholder="내용 입력..."
            rows="10"
            max-rows="15"
          ></b-form-textarea>
        </b-form-group>

        <b-button
          type="submit"
          class="m-1 "
          variant="info"
          v-if="this.type === 'register'"
          >글작성</b-button
        >
        <b-button variant="info" type="submit" class="m-1" v-else
          >글수정</b-button
        >
        <b-button variant="danger" type="reset" class="m-1">초기화</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import { writeArticle, modifyArticle, getArticle } from "@/api/notice.js";
import { mapState } from "vuex";
const memberStore = "memberStore";
export default {
  name: "NoticeInputItem",
  data() {
    return {
      article: {
        articleno: 0,
        content: "",
        hit: 0,
        subject: "",
        userid: "",
      },
      isUserid: false,
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  props: {
    type: { type: String },
  },
  created() {
    if (this.type === "modify") {
      getArticle(this.$route.params.articleno, (res) => {
        this.article = res.data;
      });
      this.isUserid = true;
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      let err = true;
      let msg = "";
      !this.userInfo.userid &&
        ((msg = "작성자 입력해주세요"),
        (err = false),
        this.$refs.userid.focus());
      err &&
        !this.article.subject &&
        ((msg = "제목 입력해주세요"),
        (err = false),
        this.$refs.subject.focus());
      err &&
        !this.article.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) alert(msg);
      else
        this.type === "register" ? this.registArticle() : this.modifyArticleM();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.subject = "";
      this.article.content = "";
    },
    registArticle() {
      let article = {
        userid: this.userInfo.userid,
        subject: this.article.subject,
        content: this.article.content,
      };
      writeArticle(article, (res) => {
        let msg = "등록 처리시 문제가 발생했습니다.";
        if (res.data === "success") {
          msg = "등록이 완료되었습니다.";
        }
        alert(msg);
        this.moveList();
      });
    },
    modifyArticleM() {
      let article = {
        articleno: this.article.articleno,
        userid: this.userInfo.userid,
        subject: this.article.subject,
        content: this.article.content,
      };
      modifyArticle(article, (res) => {
        let msg = "수정 처리시 문제가 발생했습니다.";
        if (res.data === "success") {
          msg = "수정이 완료되었습니다.";
        }
        alert(msg);
        // 현재 route를 /list로 변경.
        this.$router.push({ name: "noticeList" });
      });
    },
    moveList() {
      this.$router.push({ name: "noticeList" });
    },
  },
};
</script>

<style></style>
