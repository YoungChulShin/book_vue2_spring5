import { mount } from '@/vue/test-utils'
import RegisterPage from '@/views/RegisterPage'

describe('RegisterPage.vue', () => {

  let wrapper
  // let fieldUsername
  // let fieldEmailAddress
  // let fieldPassword
  // let buttonSubmit

  beforeEach(() => {
    wrapper = mount(RegisterPage)
    // fieldUsername = wrapper.find('#username')
    // fieldEmailAddress = wrapper.find('#emailAddress')
    // fieldPassword = wrapper.find('#password')
    // buttonSubmit = wrapper.find('form button[type="submit"]')
  })

  it ('should contain data model with initial values', () => {
    expect(wrapper.vm.form.username).toEqual('')
  })

  // it ('should render correct contents', () => {
  //   const Constructor = Vue.extend(RegisterPage);
  //   const vm = new Constructor().$mount();
  //   expect(vm.$el.querySelector('.logo').getAttribute('src')).toEqual('/static/images/logo.png');
  //   expect(vm.$el.querySelector('.tagline').textContent).toEqual('Open source task management tool');
  //   expect(vm.$el.querySelector('#username').value).toEqual('');
  //   expect(vm.$el.querySelector('#emailAddress').value).toEqual('');
  //   expect(vm.$el.querySelector('#password').value).toEqual('');
  //   expect(vm.$el.querySelector('form button[type="submit"]').textContent).toEqual('Create account');
  // })
})

