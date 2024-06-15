import React, { useEffect, useRef, useState } from "react";
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
import { createPost, uploadPostImage } from "../services/post-service";
import { getCurrentUserDetail } from "../auth/index-auth";
import { toast } from "react-toastify";

const AddPost = () => {
  const editor = useRef(null);

  const[image, setImage] = useState(null);

  const [user, setUser] = useState(undefined);

  const [categories, setCategories] = useState([]);

  const [post, setPost] = useState({
    title: "",
    content: "",
    catId: "",
  });

  useEffect(() => {
    setUser(getCurrentUserDetail());

    loadAllCategories()
      .then((data) => {
        // console.log(data);
        setCategories(data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const fieldChanged = (e) => {
    // console.log(e)
    setPost({ ...post, [e.target.name]: e.target.value });
  };

  const contentFieldChanged = (data) => {
    setPost({ ...post, content: data });
  };

  const addPost = (e) => {
    e.preventDefault();

    if (post.title.trim() === "") {
      alert("Post title is required");
      return;
    }
    if (post.content.trim() === "") {
      alert("Post content is required");
      return;
    }
    if (post.catId.trim() === "") {
      alert("Please select any one category.");
      return;
    }

    // Submit the form on server.
    // Creating another field in post to hold userId.
    post["userId"] = user.id;
    createPost(post)
      .then((data) => {
        toast.success("Post created successfully !!!");

        // upload image service
        uploadPostImage(image, data.postId)
        .then((data) =>{
          toast.success("Image uploaded !!!")
        }).catch((error) =>{
          toast.error("Error in uploading the image !!")
          console.log(error);
        })



        // Clear form fields after successful submission
        setPost({
          title: "",
          content: "",
          catId: "",
        });

        // setImage(null);

        console.log("Form fields cleared:", post); // Debugging
      })
      .catch((error) => {
        console.log(error);
      });
  };


  // Handling file change event
  const handleFileChange = (e) =>{
  
    console.log(e.target.files[0]);// coz,files is an array and when we choose our file in the form, it'll be added to the first position.
    setImage(e.target.files[0]);
  }

  return (
    <div className="wrapper">
      <Row className="mt-4">
        <Col sm={{ size: 8, offset: 2 }}>
          <Card className="mt-2 shadow border-0">
            <CardBody>
              <h3 style={{ textAlign: "center" }}>What's on your mind?</h3>
              <hr />
              <Form onSubmit={addPost}>
                {/* {JSON.stringify(post)} */}

                {/* Image uploading input */}

                <div className="mt-3">
                  <Label for="image">Post Image:</Label>
                  <Input 
                  id="image" 
                  type="file"
                  // value={image}
                  onChange={handleFileChange}
                  />
                </div>

                <div className="my-3">
                  <Label for="category">Post Category</Label>
                  <Input
                    type="select"
                    id="category"
                    placeholder="Select Category"
                    name="catId"
                    onChange={fieldChanged}
                    defaultValue={0}
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
                    onChange={fieldChanged}
                    value={post.title} // Explicitly set value to state value
                  />
                </div>
                <div className="my-3">
                  <Label for="content">Post Content</Label>
                  {/* <Input
                    style={{ height: 250 }}
                    type="textarea"
                    id="content"
                    placeholder="Your Content"
                  /> */}
                  <JoditEditor
                    ref={editor}
                    name="content"
                    onChange={(newContent) => contentFieldChanged(newContent)}
                    value={post.content} // Explicitly set value to state value
                  />
                </div>

                <Container className="text-center">
                  <Button type="submit" color="primary">
                    Create Post
                  </Button>
                  <Button className="ms-2" color="danger">
                    Reset Content
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

export default AddPost;
