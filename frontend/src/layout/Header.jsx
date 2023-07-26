import React from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'

function Header() {


  return (
    <>
      <div className="p-4 mb-4 bg-light" style={{clear: 'both'}}>
        <button type="button" 
                className="btn btn-danger btn-sm mb-3"
                style={{float: 'right'}}>Logout</button>
        <h1 className="mb-3">Jxxyi's Blog</h1>
        <h5>Welcome to my blog :)</h5>
      </div>
    </>
  )
}

export default Header

