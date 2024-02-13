import {Component} from "react";
import AuthContext from "../../others/AuthContext";
import {withTranslation} from "react-i18next";
import NavigationBar from "../../others/NavigationBar";
import Footer from "../../others/Footer";
import {Tab, Tabs} from "react-bootstrap";

class SettingsInvoices extends Component {

    static contextType = AuthContext

    state = {
        key: 'activeOrders',
        user: null,
    }

    componentDidMount() {
        const Auth = this.context
        const user = Auth.getUser()

        this.setState({user: user})
    }

    handleTabSelect = (selectedKey) => {
        this.setState({key: selectedKey});
    };

    render() {
        const {t} = this.props
        const {key} = this.state;

        return (
            <div>
                <NavigationBar/>
                <section className='d-flex justify-content-center justify-content-lg-between p-2  mt-4'></section>
                <header className={"head"}>
                    <section className={"d-flex justify-content-center"}>
                        <div className="d-flex justify-content-center w-75">
                            <div className="d-flex flex-column align-items-start">
                                <Tabs
                                    id="controlled-tab"
                                    activeKey={key}
                                    onSelect={this.handleTabSelect}
                                    className="mb-3 flex-column"
                                >
                                    <Tab eventKey="activeOrders" title={t('goTravelNamespace3:activeOrders')} />
                                    <Tab eventKey="purchasedTrips" title={t('goTravelNamespace3:purchasedTrips')} className="custom-tab"/>
                                </Tabs>
                            </div>
                        </div>
                    </section>
                </header>
                <Footer/>
            </div>
        )
    }
}

export default withTranslation()(SettingsInvoices)