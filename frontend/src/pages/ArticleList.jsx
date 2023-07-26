import React from 'react'
import { Link } from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css'

function ArticleList({ articles }) {
  return (
    <>
      <div>
        <Link to="/article-new" className="btn btn-secondary btn-sm">Creating a post</Link>
      </div>
      <br />
      <div className="row-6">
        {articles && articles.map((article, index) => {
          return (
            <>
              <div className="card" key={index}>
                  <div className="card-header">{article.id}</div>
                  <div className="card-body">
                      <h5 className="card-title">{article.title}</h5>
                      <p className="card-text">{article.content}</p>
                      <a className="btn btn-primary btn-sm" href={`/articles/${article.id}`}>more view</a>
                  </div>
              </div>
              <br />
            </>
          )
        })}

      </div>
    </>
  )
}

export default ArticleList



