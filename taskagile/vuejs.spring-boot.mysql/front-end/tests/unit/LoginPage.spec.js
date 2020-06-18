import Vue from 'vue'
import LoginPage from '@/views/LoginPage'

describe('LoginPage.vue', () => {
  it ('should render correct contents', () => {
    const Constructor = Vue.extend(LoginPage)
      const vm = new Constructor().$mount() // Mount 호출을 통해서 렌더링 된 것처럼 설정할 수 있다
      expect(vm.$el.querySelector('h1').textContent)
        .toEqual('TestAgile')
  })
})
