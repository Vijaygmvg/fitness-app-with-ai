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
        console.log(token + "setting the credentials")
         dispatch(setCredentials({token,user:tokenData}))
        setAuthReady(true)
      }
    
  },[token,tokenData,dispatch])
    const handleLogout =  () => {
        logOut(); 
        dispatch(logout())
      
    
   
 

         
  };
  return (<>
  
      <div className='flex flex-row  justify-end'>
      {
        !(token)?
     <button
  onClick={() => logIn()}
  className="px-6 py-2 bg-blue-600 text-white font-semibold rounded-lg shadow-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition duration-150"
>
  Login
</button>:
      // <> {JSON.stringify(tokenData,null,2)}
        <>
        <br></br>
       <button
  onClick={handleLogout}
  className="px-6 py-2 bg-red-500 text-white font-semibold rounded-lg shadow-md hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-400 focus:ring-offset-2 transition duration-150"
>
  Logout
</button>

        </>
      
}
</div>
       <Routes>
        <Route path="/activities" element={<ActivitiesPage/>}/>
          
        
        <Route path="activities/:id" element={<ActivityDetail/>} />
          
        <Route path="/" element={token?<Navigate to="/activities" replace/> :
      <div> welcome login please</div>} />
       </Routes>
    
  
  </>)
}

export default App
