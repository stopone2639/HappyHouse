<template>
  <b-container class="bv-example-row mt-3 flex-direction-col">
    <notice-input-item type="modify" />
  </b-container>
</template>

<script>
import NoticeInputItem from "@/components/notice/NoticeInputItem.vue";

export default {
  name: "NoticeModify",
  components: {
    NoticeInputItem,
  },
};
</script>

<style></style>
