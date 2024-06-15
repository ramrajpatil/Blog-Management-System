import React, { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Button, Card, CardBody, CardText } from "reactstrap";
import { getCurrentUserDetail, isLoggedIn } from "../auth/index-auth";
import userContext from "../contexts/userContext";

const Posts = ({
  post = {
    title: "This is default post title.",
    content: "This is default post content.",
  },
  deletePost,
}) => {
  // console.log(post);//Debugging.

  const userContextData = useContext(userContext);

  const [user, setUser] = useState([]);

  const [login, setLogin] = useState(false);

  useEffect(() => {
    setUser(getCurrentUserDetail());
    setLogin(isLoggedIn());
  }, []);

  return (
    <Card className="border-0 shadow mt-3">
      <CardBody>
        <h2>{post.title}</h2>
        {/* We want to limit the description of the content to be showed on the home page. */}
        {/* That's why we have used subString() method to show only first 20 characters. */}
        {/* Also We want to print the content on the web page in HTML format */}
        {/* So we'll be using <CardText dangerouslySetInnerHTML={{__html:post.content}}> */}

        <CardText
          dangerouslySetInnerHTML={{
            __html: post.content.substring(0, 25) + "...",
          }}
        ></CardText>
        <div>
          {/* <Button onClick={nextPage} color="dark">
            Read More
          </Button> */}
          <Link to={"/posts/" + post.postId} className="btn btn-dark">
            Read More
          </Link>

          {/* This delete button will be visible only if the current logged in user has posted that particular post. */}
          {/* Two turnery conditions have been used. */}

          {userContextData.user.login &&
            (user.id === post.user.id ? (
              <Button tag={Link} to={`/user/update-blog/${post.postId}`} color="warning" className="ms-2">
                Update
              </Button>
            ) : (
              ""
            ))}

          {userContextData.user.login &&
            (user.id === post.user.id ? (
              <Button
                color="danger"
                className="ms-2"
                onClick={() => deletePost(post)}
              >
                Delete
              </Button>
            ) : (
              ""
            ))}
        </div>
      </CardBody>
    </Card>
  );
};

export default Posts;
