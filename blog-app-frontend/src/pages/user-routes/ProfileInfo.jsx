import React, { useContext } from "react";
import Base from "../../components/Base";
import { getCurrentUserDetail } from "../../auth/index-auth";
import {
  Card,
  CardBody,
  CardHeader,
  CardTitle,
  Col,
  Container,
  Row,
  Table,
} from "reactstrap";

const ProfileInfo = () => {
  const user = getCurrentUserDetail();
  console.log(user);

  const userView = () => {
    return (
      <Row>
        <Col md={{ size: 8, offset: 2 }}>
          <Container className="mt-3">
            <Card className="shadow">
              <CardBody>
                <Container className="image-container text-center">
                  <img
                    className="text-center mt-3"
                    style={{ width: 120 }}
                    src="https://static-00.iconduck.com/assets.00/profile-default-icon-2048x2045-u3j7s5nj.png"
                    alt="profile photo"
                  />
                </Container>
                <h1 className="text-uppercase mt-2 text-center">
                  {user && user.name}
                </h1>
                <Table responsive hover bordered={false} className="mt-2">
                  <tbody>
                    <tr>
                      <td>MyBlogsId</td>
                      <td>:</td>
                      <td>MBID{user.id}</td>
                    </tr>
                    <tr>
                      <td>Email/Username</td>
                      <td>:</td>
                      <td>{user.email}</td>
                    </tr>
                    <tr>
                      <td>About</td>
                      <td>:</td>
                      <td>{user.about}</td>
                    </tr>
                    <tr>
                      <td>Role</td>
                      <td>:</td>
                      <td>
                        {user.roles.map((role) => {
                          return <div key={role.id} >{role.name}</div>;
                        })}
                      </td>
                    </tr>
                  </tbody>
                </Table>
              </CardBody>
            </Card>
          </Container>
        </Col>
      </Row>
    );
  };
  return (
    <Base>
    {user ? userView() : 'Loading user data...'}
    
    </Base>
  );
};

export default ProfileInfo;
