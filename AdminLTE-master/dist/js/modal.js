Vue.component('Modal', {
  template: '#modal-template',
  props: ['show'],
  methods: {
    close: function () {
      this.$emit('close');
    }
  },
  mounted: function () {
    document.addEventListener("keydown", (e) => {
      if (this.show && e.keyCode == 27) {
        this.close();
      }
    });
  }
});

Vue.component('NewPostModal', {
  template: '#new-post-modal-template',
  props: ['show'],
  data: function () {
    return {
      title: '',
      body: ''
    };
  },
  methods: {
    close: function () {
      this.$emit('close');
      this.title = '';
      this.body = '';
    },
    savePost: function () {
      // Some save logic goes here...
      
      this.close();
    }
  }
});

Vue.component('NewCommentModal', {
  template: '#new-comment-modal-template',
  props: ['show'],
  data: function () {
    return {
      comment: ''
    };
  },
  methods: {
    close: function () {
      this.$emit('close');
      this.comment = '';
    },
    postComment: function () {
      // Some save logic goes here...
      
      this.close();
    }
  }
});

new Vue({
  el: '#app',
  data: {
    showNewPostModal: false,
    showNewCommentModal: false
  }
});