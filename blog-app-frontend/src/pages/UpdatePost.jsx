import React, { useContext, useEffect, useState, useRef } from "react";
import Base from "../components/Base";
import { useNavigate, useParams } from "react-router-dom";
import userContext from "../contexts/userContext";
import { loadSinglePost, updatePostService } from "../services/post-service";
import { toast } from "react-toastify";
import {
  Button,
  Card,
  CardBody,
  Container,
  Form,
  Input,
  Row,
  Col,
  Label,
} from "reactstrap";
import { loadAllCategories } from "../services/category-service";
import JoditEditor from "jodit-react";

const UpdatePost = () => {
  const editor = useRef(null);

  const [categories, setCategories] = useState([]);
  const { postId } = useParams();
  const object = useContext(userContext);
  const navigator = useNavigate();
  const [post, setPost] = useState(null);

  useEffect(() => {
    // Load the post from the database.
    loadSinglePost(postId)
      .then((data) => {
        console.log(data);
        // Also adding new field
        setPost({ ...data, categoryId: data.category.id });
      })
      .catch((error) => {
        console.log(error);
        toast.error("Error in loading the post !!!");
      });

    // Loading categories first
    loadAllCategories()
      .then((data) => {
        // console.log(data);
        setCategories(data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  // To check if this is actually the post created by loggedIn user.
  useEffect(() => {
    if (post) {
      if (post.user.id !== object.user.data.id) {
        toast.error("This is not your post, you cannot edit it.");
        navigator("/");
      }
    }
  }, [post]);

  const handleChange = (e, fieldName) => {
    setPost({
      ...post,
      [fieldName]: e.target.value,
    });
  };

  const updatePost = (e) => {
    e.preventDefault();
    // console.log(post);
    // as it is post object, but we are changing only category id to update it too.
    updatePostService({...post, category:{id:post.categoryId}}, post.postId)
    .then((data) =>{
        console.log("After update");
        console.log(data);
        toast.success("Post updation successful !!!")
    })
    .catch((error) =>{
        console.log(error);
        toast.error("Post updation failed !!!")
    })
  };

  const updateHtml = () => {
    if (!post) {
      return null; // or a loading indicator
    }
    return (
      <div className="wrapper">
        <Row className="mt-4">
          <Col sm={{ size: 8, offset: 2 }}>
            <Card className="mt-2 shadow border-0">
              <CardBody>
                <h3 style={{ textAlign: "center" }}>Update Post</h3>
                <hr />
                <Form onSubmit={updatePost}>
                  {/* {JSON.stringify(post)} */}

                  {/* Image uploading input */}

                  <div className="mt-3">
                    <Label for="image">Post Image:</Label>
                    <Input
                      id="image"
                      type="file"
                      // value={image}
                      //   onChange={""}
                    />
                  </div>

                  <div className="my-3">
                    <Label for="category">Post Category</Label>
                    <Input
                      type="select"
                      id="category"
                      placeholder="Select Category"
                      name="catId"
                      onChange={(e) => {
                        handleChange(e, "categoryId");
                      }}
                      value={post.categoryId} // Extra field that we added.
                    >
                      <option disabled value={0}>
                        --Select Category--
                      </option>

                      {categories.map((c) => (
                        <option key={c.id} value={c.id}>
                          {c.categoryTitle}
                        </option>
                      ))}
                    </Input>
                  </div>

                  <div className="my-3">
                    <Label for="title">Post Title</Label>
                    <Input
                      type="text"
                      id="title"
                      placeholder="Your title"
                      name="title"
                      onChange={(e) => {
                        handleChange(e, "title");
                      }}
                      value={post.title} // Explicitly set value to state value
                    />
                  </div>
                  <div className="my-3">
                    <Label for="content">Post Content</Label>

                    <JoditEditor
                      ref={editor}
                      name="content"
                      onChange={(newContent) =>
                        setPost({ ...post, content: newContent })
                      }
                      value={post.content} // Explicitly set value to state value
                    />
                  </div>

                  <Container className="text-center">
                    <Button type="submit" color="warning">
                      Update
                    </Button>
                    <Button className="ms-2" color="danger">
                      Reset
                    </Button>
                  </Container>
                </Form>
              </CardBody>
            </Card>
          </Col>
        </Row>
      </div>
    );
  };

  return (
    <Base>
      <Container>{updateHtml()}</Container>
    </Base>
  );
};

export default UpdatePost;
