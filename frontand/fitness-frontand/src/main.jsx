import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import { Provider } from 'react-redux'
import store from './store/store'
import {AuthProvider} from 'react-oauth2-code-pkce'
import { authConfig } from './authConfig'

import App from './App'
import { BrowserRouter } from 'react-router-dom'


const root = ReactDOM.createRoot(document.getElementById('root'))
root.render(
  <AuthProvider authConfig={authConfig}>
  <Provider store={store}>
     <BrowserRouter>
    <App />
    </BrowserRouter>
  </Provider>
  </AuthProvider>,
)