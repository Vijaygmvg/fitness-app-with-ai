import { Activity, useContext, useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { AuthContext } from 'react-oauth2-code-pkce'
import { useDispatch } from 'react-redux'
import { logout, setCredentials } from './store/authSlice'
import { Routes, Route, Navigate } from 'react-router-dom';

import ActivityDetail from './components/ActivityDetail'
import ActivityForm from './components/ActivityForm'
import ActivityList from './components/ActivityList'

const ActivitiesPage=()=>{

  const refresh=()=>{
    window.location.reload()
  }
  return (
    <div>
      <ActivityForm  onActivitiesAdded={refresh}/>
      <ActivityList/>
    </div>
  )
}

function App() {

  const {token,tokenData,logIn,logOut,isAuthenticated}=useContext(AuthContext);
  const dispatch=useDispatch()
  const [authReady,setAuthReady]=useState(false)
  useEffect(()=>{
      if(token){
         dispatch(setCredentials({token,user:tokenData}))
        setAuthReady(true)
      }
    
  },[token,tokenData,dispatch])
    const handleLogout = () => {
    dispatch(logout()); 
    logOut();           
  };
  return (<>
  <div>
    hello
    <div className='text-red-200'>
  lknsflnflnf
    </div>
    <div>
      {
        !token?
      <button className='bg-blue-700'  onClick={()=>{logIn()}}> onclick</button>:
      // <> {JSON.stringify(tokenData,null,2)}
        <>
        <br></br>
        <button className='bg-red-400' onClick={handleLogout}>logout</button>
        </>
      
}
       <Routes>
        <Route path="/activities" element={<ActivitiesPage/>}/>
          
        
        <Route path="activities/:id" element={<ActivityDetail/>} />
          
        <Route path="/" element={token?<Navigate to="/activities" replace/> :
      <div> welcome login please</div>} />
       </Routes>
    </div>
  </div>
  </>)
}

export default App
