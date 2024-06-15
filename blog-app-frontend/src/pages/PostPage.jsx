import React, { useEffect, useState } from "react";
import Base from "../components/Base";
import { Link, useParams } from "react-router-dom";
import { creatComment, loadSinglePost } from "../services/post-service";
import {
  Button,
  Card,
  CardBody,
  CardText,
  Col,
  Container,
  Input,
  Row,
} from "reactstrap";
import { toast } from "react-toastify";

import { BASE_URL } from "../services/helper";
import { getCurrentUserDetail, isLoggedIn } from "../auth/index-auth";

const PostPage = () => {
  const { postId } = useParams();

  const [post, setPost] = useState([]);

  const [currentUser, setCurrentUser] = useState([]);

  const [comment, setComment] = useState({
    content: "",
  });

  useEffect(() => {
    setCurrentUser(getCurrentUserDetail());
  }, []);
  // Just to check if we have got the current user details or not as
  // We will be needing that userId when adding a comment along with postId.
  // console.log(currentUser);

  useEffect(() => {
    loadSinglePost(postId)
      .then((data) => {
        setPost(data);
        console.log(data);
      })
      .catch((error) => {
        console.log(error);
        toast.error("Error in loading post data.");
      });
  }, []);

  const printDate = (numbers) => {
    return new Date(numbers).toLocaleDateString();
  };

  const submitComment = () => {

    // To allow comments to be added only if the use is loggedIn.
    if(!isLoggedIn()){
      toast.error("You need to login first to comment.")
      return;
    }


    // To prevent from blank comments getting added.
    if(comment.content.trim() === ''){
      return;
    }

    creatComment(comment, currentUser.id, post.postId)
      .then((data) => {
        console.log(data);
        toast.success("Comment added to the current post !!!");
        // To avoid refreshing to show the added comment, we can push the
        // Newly added comment into post data.
        setPost({
          ...post,
          comments: [...post?.comments, data.data],
        });
        setComment({
          content: "",
        });
      })
      .catch((error) => {
        console.log(error);
        toast.error("Adding comment failed !!!");
      });


  };

  const goToTop =()=>{
    window.scroll(0,0);
  }

  return (
    <Base>
      <Container className="mt-4">
        <Link to={"/"}>Home</Link> / {post && <Link to="">{post?.title}</Link>}
        <Row>
          <Col md={{ size: 12 }}>
            <Card className="mt-3 shadow border-0">
              {post && (
                <CardBody>
                  <CardText>
                    Posted By <b>{post?.user?.name}</b> on{" "}
                    <b>{printDate(post?.addDate)}</b>
                  </CardText>

                  <CardText>
                    <span className="text-muted">
                      Category: {post?.category?.categoryTitle}
                    </span>
                  </CardText>
                  <hr />

                  <CardText className="mt-3 ps-2">
                    <h1>{post?.title}</h1>
                  </CardText>
                  <div
                    className="image-container mt-4 shadow"
                    style={{ maxWidth: "70%" }}
                  >
                    <img
                      className="img-fluid"
                      src={BASE_URL + "/api/post/image/" + post.imageName}
                      alt=""
                    />
                  </div>

                  <CardText
                    className="mt-5"
                    dangerouslySetInnerHTML={{ __html: post.content }}
                  ></CardText>
                </CardBody>
              )}
            </Card>
          </Col>
        </Row>
        {/* Comments section start. */}
        <Row className="my-4">
          <Col md={{ size: 8, offset: 1 }}>
            <h3>Comments: ({post ? post?.comments?.length : 0})</h3>

            {post?.comments &&
              post?.comments.map((c, index) => (
                <Card key={index} className="mt-2 border-0">
                  <CardBody>
                    <CardText>
                      <h5>{c?.content}</h5>
                    </CardText>
                  </CardBody>
                </Card>
              ))}

            <Card className="mt-2 border-0">
              <CardBody>
                <Input
                  type="textarea"
                  placeholder="Comment here"
                  name="content"
                  value={comment.content}
                  onChange={(e) => setComment({ content: e.target.value })}
                />
                <Button onClick={submitComment} className="mt-2" color="primary">
                  Submit
                </Button>
                
                  <Button  color="primary" className="mt-2 ms-5" onClick={goToTop}>Go to Top</Button>
                
              </CardBody>
            </Card>
          </Col>
        </Row>
      </Container>
    </Base>
  );
};

export default PostPage;
