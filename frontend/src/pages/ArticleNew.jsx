import React from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'

function ArticleNew() {
  
  return (
    <>
      <article>
        <div className="mb-4">
            <input type="text" className="form-control" placeholder="Title" />
        </div>
        <section className="mb-4">
            <textarea className="form-control h-25" rows="10" placeholder="Content"/>
        </section>
        <div style={{textAlign: 'right'}}>
          <button type="button" className="btn btn-secondary btn-sm">Cancel</button>
          <button type="button" 
                  className="btn btn-primary btn-sm" 
                  style={{marginLeft: '0.5rem'}}>Submit</button>
        </div>
      </article>
    </>
  )
}

export default ArticleNew