<template>
  <b-tr>
    <b-td v-html="title"></b-td>
    <b-td>{{ category }}</b-td>
    <b-td>{{ address }}</b-td>
    <b-td>{{ roadAddress }}</b-td>
    <b-td @click="moveLink" class="text-center">
      <b-button type="button" variant="warning" @click="getFoodList">
        검색
      </b-button>
    </b-td>
  </b-tr></template
>
<script>
export default {
  name: "FoodListItem",
  props: {
    title: String,
    category: String,
    address: String,
    roadAddress: String,
    link: String,
  },
  methods: {
    moveLink() {
      window.open(
        "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=" +
          // eslint-disable-next-line prettier/prettier
          this.setTitle(this.title)
      );
    },
    setTitle(title) {
      let str = title.replace("<b>", " ");
      str = str.replace("</b>", " ");
      return str;
    },
  },
};
</script>

<style></style>
