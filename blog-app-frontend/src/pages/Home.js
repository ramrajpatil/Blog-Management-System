import Base from "../components/Base";
import NewFeed from "../components/NewFeed";
import { Col, Container, Row } from "reactstrap";
import NewFeedInfinite from "../components/NewFeedInfinite";
import CategorySideMenu from "../components/CategorySideMenu";

const Home = () => {
  return (
    <Base>
      <Container className="mt-5">
        {/* <NewFeed /> */}
        <Row >
          <Col md={3} className="mt-5 pt-3" >
            <CategorySideMenu />
          </Col>
          <Col md={9}>
            <NewFeedInfinite />
          </Col>
        </Row>
      </Container>
    </Base>
  );
};

export default Home;
