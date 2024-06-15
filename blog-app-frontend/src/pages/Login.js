import React, { useContext, useState } from "react";
import Base from "../components/Base";
import {
  Card,
  CardBody,
  Label,
  Input,
  CardHeader,
  Col,
  Button,
  Container,
  FormGroup,
  Row,
} from "reactstrap";
import { toast } from "react-toastify";
import { loginUser } from "../services/user-service";
import { doLogin } from "../auth/index-auth";
import { useNavigate } from "react-router-dom";
import userContext from "../contexts/userContext";


const Login = () => {

  const userContextData = useContext(userContext)

  const [loginDetail, setLoginDetail] = useState({
    username: "",
    password: "",
  });

  const navigator = useNavigate();

  const handleChange = (e, field) => {
    let actualValue = e.target.value;
    setLoginDetail({ ...loginDetail, [field]: actualValue });
  };

  const handleReset = () => {
    setLoginDetail({
      username: "",
      password: "",
    });
  };

  const handleFormSubmit = (e) => {
    e.preventDefault();
    // console.log(loginDetail);

    // Validation
    if (
      loginDetail.username.trim() === "" ||
      loginDetail.password.trim() === ""
    ) {
      toast.error("username or password is required !!");
    }

    // submit the data to server to generate token.
    loginUser(loginDetail)
      .then((udata) => {
        console.log(udata);
        // Save the data to local storage.
        doLogin(udata, () =>{
          // This is the callback function to redirect the user to next page
          // console.log("login detail is saved in localstorage");
          // redirect to usuer dashboard page.


          userContextData.setUser({
            data:udata.user,
            login:true
          })

          navigator("/user/dashboard")


        })


        toast.success("Login successful !!!")

      })
      .catch((error) => {
        // console.log("error block");
        console.log(error);
        if (error.response.status === 400 || error.response.status === 404) {
          toast.error(error.response.data.message);
        } else toast.error("Something went wrong on server.");
      });
  };

  return (
    <Base>
      <Container>
        <Row className="mt-4">
          <Col sm={{ size: 6, offset: 3 }}>
            <Card color="dark" inverse>
              <CardHeader>
                <h3 style={{ textAlign: "center" }}>Login</h3>
              </CardHeader>
              <CardBody>
                <form onSubmit={handleFormSubmit}>
                  <FormGroup>
                    <Label className="mt-2" for="email">
                      Username/Email:
                    </Label>
                    <Input
                      type="email"
                      placeholder="Enter here"
                      id="email"
                      value={loginDetail.username}
                      onChange={(e) => handleChange(e, "username")}
                    />
                  </FormGroup>
                  <FormGroup>
                    <Label className="mt-2" for="password">
                      Password:
                    </Label>
                    <Input
                      type="password"
                      placeholder="Enter here"
                      id="password"
                      value={loginDetail.password}
                      onChange={(e) => handleChange(e, "password")}
                    />
                  </FormGroup>
                  <Container className="text-center">
                    <Button outline color="light">
                      Login
                    </Button>
                    <Button
                      color="secondary"
                      className="ms-2"
                      type="reset"
                      onClick={handleReset}
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

export default Login;
