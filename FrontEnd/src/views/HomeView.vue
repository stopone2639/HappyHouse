<template>
  <div class="wrapper">
    <parallax class="page-header header-filter" :style="headerStyle">
      <div class="md-layout">
        <div class="md-layout-item">
          <div class="image-wrapper">
            <div class="brand">
              <b-row>
                <h1>HappyHouse</h1>
              </b-row>
              <b-row class="justify-content-center">
                <md-button class="md-warning" @click.prevent="goHouseView"
                  >아파트 거래 조회</md-button
                >
              </b-row>
            </div>
          </div>
        </div>
      </div>
    </parallax>
    <!-- <div class="main main-raised">
      <div class="section section-basic" id="main">
        <div class="container">
          <div class="title">
            <h2>홈페이지 소개 및 기능 소개</h2>
          </div>
        </div>
      </div>
    </div> -->
  </div>
</template>

<script>
export default {
  components: {},
  name: "index",
  bodyClass: "index-page",
  props: {
    image: {
      type: String,
      default: require("@/assets/img/house-bg.png"),
    },
  },
  data() {
    return {
      firstname: null,
      email: null,
      password: null,
      leafShow: false,
    };
  },
  methods: {
    leafActive() {
      if (window.innerWidth < 768) {
        this.leafShow = false;
      } else {
        this.leafShow = true;
      }
    },
    goHouseView() {
      this.$router.push({ name: "house" });
    },
  },
  computed: {
    headerStyle() {
      return {
        backgroundImage: `url(${this.image})`,
      };
    },
    signupImage() {
      return {
        backgroundImage: `url(${this.signup})`,
      };
    },
  },
  mounted() {
    this.leafActive();
    window.addEventListener("resize", this.leafActive);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.leafActive);
  },
};
</script>
<style lang="scss">
.section-download {
  .md-button + .md-button {
    margin-left: 5px;
  }
}

@media all and (min-width: 991px) {
  .btn-container {
    display: flex;
  }
}
</style>
