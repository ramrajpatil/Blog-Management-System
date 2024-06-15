import React from 'react'
import CustomNavbar from './CustomNavbar'
import CustomFooter from './CustomFooter'

const Base = ({titile="Welcome to our website", children}) => {
  return (
    <div className='container-fluid p-0 m-0'>
       <CustomNavbar/>

        { children }

        {/* <CustomFooter/> */}
    </div>
  )
}

export default Base