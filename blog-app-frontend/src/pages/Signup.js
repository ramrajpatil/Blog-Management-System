import React, { useEffect, useState } from "react";
import Base from "../components/Base";
import {
  Button,
  Card,
  CardBody,
  CardHeader,
  Col,
  Container,
  FormFeedback,
  FormGroup,
  Input,
  Label,
  Row,
} from "reactstrap";
import { signUp } from "../services/user-service";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

const Signup = () => {
  const [data, setData] = useState({
    name: "",
    email: "",
    password: "",
    about: "",
  });

  const navigator = useNavigate();

  // handleChange
  const handleChange = (e, property) => {
    // Dynamically setting the values using 'property'
    setData({ ...data, [property]: e.target.value });
  };

  // Resetting the form
  const resetData = () => {
    setData({
      name: "",
      email: "",
      password: "",
      about: "",
    });
  };

  // Submitting the form
  const submitForm = (e) => {
    e.preventDefault();

    // if(error.isError){
    //   toast.error("Form data is invalid, correct all details and then submit !!")
    //   setError({...error, isError:false})
    //   return ;
    // }

    console.log(data);
    // Data validate

    // Call server api for sending the data.
    signUp(data)
      .then((response) => {
        console.log(response);
        console.log("success log");
        toast.success(
          "User registered successfully with userId: " + response.id
        );
        setData({
          name: "",
          email: "",
          password: "",
          about: "",
        });
        navigator("/login");
      })
      .catch((error) => {
        console.log(error);
        console.log("Error log");

        // Handling errors in proper way
        setError({
          errors: error,
          isError: true,
        });
      });
  };
  const [error, setError] = useState({
    errors: {},
    isError: false,
  });
  return (
    <Base>
      <Container>
        {/* To get all elements inside this tag in center. */}

        <Row className="mt-4">
          <Col sm={{ size: 6, offset: 3 }}>
            <Card color="dark" inverse>
              <CardHeader>
                <h3 style={{ textAlign: "center" }}>Register Now</h3>
              </CardHeader>
              <CardBody>
                <form onSubmit={submitForm}>
                  <FormGroup>
                    {/*Name field */}
                    <Label for="name">Name: </Label>
                    <Input
                      type="text"
                      placeholder="Enter here"
                      id="name"
                      onChange={(e) => handleChange(e, "name")}
                      value={data.name}
                      // using null safe : ?
                      invalid={
                        error.errors?.response?.data?.name ? true : false
                      }
                    />
                    <FormFeedback>
                      {error.errors?.response?.data?.name}
                    </FormFeedback>
                  </FormGroup>

                  <FormGroup>
                    {/*Email field */}
                    <Label className="mt-2" for="email">
                      Email:{" "}
                    </Label>
                    <Input
                      type="email"
                      placeholder="Enter here"
                      id="email"
                      onChange={(e) => handleChange(e, "email")}
                      value={data.email}
                      // using null safe : ?
                      invalid={
                        error.errors?.response?.data?.email ? true : false
                      }
                    />
                    <FormFeedback>
                      {error.errors?.response?.data?.email}
                    </FormFeedback>
                  </FormGroup>
                  <FormGroup>
                    {/*Password field */}
                    <Label className="mt-2" for="password">
                      Password:{" "}
                    </Label>
                    <Input
                      type="password"
                      placeholder="Enter here"
                      id="password"
                      onChange={(e) => handleChange(e, "password")}
                      value={data.password}
                      // using null safe : ?
                      invalid={
                        error.errors?.response?.data?.password ? true : false
                      }
                    />
                    <FormFeedback>
                      {error.errors?.response?.data?.password}
                    </FormFeedback>
                  </FormGroup>
                  <FormGroup>
                    {/*About field */}
                    <Label className="mt-2" for="about">
                      About:{" "}
                    </Label>
                    <Input
                      type="textarea"
                      placeholder="Enter here"
                      id="about"
                      style={{ height: "200px" }}
                      onChange={(e) => handleChange(e, "about")}
                      value={data.about}
                      // using null safe : ?
                      invalid={
                        error.errors?.response?.data?.about ? true : false
                      }
                    />
                    <FormFeedback>
                      {error.errors?.response?.data?.about}
                    </FormFeedback>
                  </FormGroup>
                  {/*Buttons */}
                  <Container className="text-center">
                    <Button outline color="light">
                      SignUp
                    </Button>
                    <Button
                      onClick={resetData}
                      color="secondary"
                      className="ms-2"
                      type="reset"
                    >
                      Reset
                    </Button>
                  </Container>
                </form>
              </CardBody>
            </Card>
          </Col>
        </Row>
      </Container>
    </Base>
  );
};

export default Signup;
