import React, { Component } from 'react'
import './Home.css'
import Carousel from 'react-bootstrap/Carousel';
import {TbBeach} from "react-icons/tb"
import {TiWeatherSunny} from "react-icons/ti"
import Turkiye from '../../assets/image/turkiye.PNG'
import Cyprus from '../../assets/image/cyprus.png'
import Tunisia from '../../assets/image/tunisia.svg'
import Italy from '../../assets/image/italy.svg'
import Spain from '../../assets/image/spain.svg'
import Croatia from '../../assets/image/croatia.svg'
import Bulgaria from '../../assets/image/bulgaria.svg'
import Greece from '../../assets/image/greece.svg'
import Holiday from '../../assets/image/holiday.png'
import {Container} from "react-bootstrap";
import Footer from "../../others/Footer";
import NavigationBar from "../../others/NavigationBar";
import {orderApi} from "../../others/OrderApi";

class Home extends Component {

    state = {
        countries: [],
        transports: [],
        selectedTransport: '',
        selectedTransportId: 0,
        selectedCountry: '',
        selectedCountryId: 0,
        numberOfDaysMin: 0,
        numberOfDaysMax: 0,
    }

    componentDidMount() {
        this.handleGetTransports()
        this.handleGetCountries()
    }

    handleGetTransports = () => {
        orderApi.getTransports().then(res => {
            this.setState({transports: res.data})
        })
    }

    handleGetCountries = () => {
        orderApi.getCountries().then(res => {
            this.setState({countries: res.data})
        })
    }

    handleChangeCountry = (e) => {
        this.setState({selectedCountry: e.target.value})

        this.state.countries.map(country => {
            if (country.nameCountry === e.target.value) {
                this.setState({selectedCountryId: country.idCountry, selectedCountry: ''})
            } else if (e.target.value === '') {
                this.setState({selectedCountryId: 0, selectedCountry: ''})
            }
        })
    }

    handleChangeTransport = (e) => {
        this.setState({selectedTransport: e.target.value})

        this.state.transports.map(tran => {
            if (tran.nameTransport === e.target.value) {
                this.setState({selectedTransportId: tran.idTransport, selectedTransport: ''})
            } else if (e.target.value.trim() === "") {
                this.setState({selectedTransportId: 0, selectedTransport: ''})
            }
        })
    }

    handleChangeNumberOfDays = (e) => {

        if(e.target.value === "Ilekolwiek"){
            this.setState( {numberOfDaysMin: 1, numberOfDaysMax: 100})
        }else if(e.target.value === "> 15 dni") {
            this.setState( {numberOfDaysMin: 15, numberOfDaysMax: 100})
        }else{
            const textArr = e.target.value.split(" - ")
            const textArr2 = textArr[1].split(" dni")
            this.setState( {numberOfDaysMin: textArr[0], numberOfDaysMax: textArr2[0]})
        }





    }

    handleClickSearch = () => {
        window.location.href = "/searchedTrips/" + this.state.selectedCountryId + "/" + this.state.selectedTransportId + "/" + this.state.numberOfDaysMin + "/" + this.state.numberOfDaysMax
    }

    render() {
            return (
                <main>
                    <NavigationBar/>
                    <header >
                        <section className="search-sec">
                            <div className="container">
                                <form action="src/main/frontend/src/pages/home/Home#" method="post" noValidate="novalidate">
                                    <div className="row">
                                        <div className="col-lg-12">
                                            <div className="row">
                                                <div className="col-lg-3 col-md-3 col-sm-12 p-0">
                                                    <p className="search-desc">Dokąd chcesz jechać ?</p>
                                                    <select className="form-control search-slt" id="exampleFormControlSelect1"
                                                            name={"selectedCountry"} onChange={this.handleChangeCountry}>
                                                        <option value={this.state.selectedCountry}>Gdziekolwiek</option>
                                                        {this.state.countries.map(country =>
                                                            <option key={country.idCountry}>{country.nameCountry}</option>
                                                        )}
                                                    </select>
                                                </div>
                                                <div className="col-lg-3 col-md-3 col-sm-12 p-0">
                                                    <p className="search-desc">Jak długo ?</p>
                                                    <select className="form-control search-slt" id="exampleFormControlSelect1" onChange={this.handleChangeNumberOfDays}>
                                                        <option>Ilekolwiek</option>
                                                        <option>1 - 5 dni</option>
                                                        <option>5 - 10 dni</option>
                                                        <option>10 - 15 dni</option>
                                                        <option> > 15 dni</option>
                                                    </select>
                                                </div>
                                                <div className="col-lg-3 col-md-3 col-sm-12 p-0">
                                                    <p className="search-desc">Czym ?</p>
                                                    <select className="form-control search-slt" id="exampleFormControlSelect1"
                                                            name={"selectedTransport"} onChange={this.handleChangeTransport}>

                                                        <option value={this.state.selectedTransport}>Czymkolwiek</option>
                                                        {this.state.transports.map(transport =>
                                                            <option key={transport.idTransport}>{transport.nameTransport}</option>
                                                        )}
                                                    </select>
                                                </div>
                                                <div className="col-lg-3 col-md-3 col-sm-12 p-0">
                                                    <button type="button" className="btn btn-danger wrn-btn" onClick={this.handleClickSearch}>Szukaj</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </section>
                        <section className='d-flex justify-content-center justify-content-lg-between p-1 '></section>
                        <Container>
                            <section >
                                <center>
                                    <Carousel>
                                        <Carousel.Item interval={1000} >
                                            <img
                                                className="d-block"
                                                src={Turkiye}
                                                alt=" "
                                            />
                                            <Carousel.Caption>
                                                <h3 className={"carusel-first"}>TURCJA I EGIPT<br/>NA ZIMĘ 2023</h3>
                                            </Carousel.Caption>
                                        </Carousel.Item>
                                        <Carousel.Item interval={1000}>
                                            <img
                                                src={Holiday}
                                                alt=""
                                            />
                                            <Carousel.Caption className={"carusel-second"}>
                                                <h3> LATO 2023 - <br/>PRZEDSPRZEDAŻ</h3>
                                                <p>
                                                    <ul>
                                                        <li>
                                                            Zaliczki od 15%
                                                        </li>
                                                        <li>
                                                            Zaliczki od 15%
                                                        </li>
                                                        <li>
                                                            Bezpłatna zmiana rezerwacji
                                                        </li>
                                                    </ul>
                                                </p>
                                            </Carousel.Caption>
                                        </Carousel.Item>
                                        <Carousel.Item interval={1000}>
                                            <img
                                                src={Cyprus}
                                                alt=""
                                            />
                                            <Carousel.Caption className={"carusel-third"}>
                                                <h3>Cypr<br/>Więcej do odkrycia</h3>
                                                <p>
                                                    <ul>
                                                        <li>
                                                            <TiWeatherSunny/> imponujące plaże i klify
                                                        </li>
                                                        <li>
                                                            <TbBeach/> sielski klimat
                                                        </li>
                                                    </ul>
                                                </p>
                                            </Carousel.Caption>
                                        </Carousel.Item>
                                    </Carousel>
                                </center>
                            </section>
                        </Container>
                        <section className='d-flex justify-content-center justify-content-lg-between p-1 '></section>

                        <Container>
                            <section>
                                <div className="index-header">
                                    <center><h2 className="main">Polecane kierunki:</h2></center>
                                    <hr className="main-line"></hr>

                                    <div className="row">
                                        <div className="col-lg-4 col-md-6 trip_dir wlochy">
                                            <div className="country-image-container">
                                                <img src={Italy} alt={""}/>
                                                <div className="highlight_trip">
                                                    <h3>Włochy</h3>
                                                    <span className="trip-price"> od 1430 zł/os.</span>
                                                    <div className="dark_cover">
                                                        <div className="info-container">
                                                            <div className="trip_desc">
                                                                przyciągają zabytkami i dziełami sztuki, śródziemnomorską kuchnią. Swoją wielowiekową historią, bogatą kulturą i awangardową modą. Wciąż proponują nowe atrakcje. Do tego szybki lot samolotem, bo około 2 godz. Włochy są uwielbiane przez niemal wszystkich, a w szczególności przez zakochanych.
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div className="col-lg-4 col-md-6 trip_dir tunezja">
                                            <div className="country-image-container">
                                                <img src={Tunisia} alt={""}/>
                                                <div className="highlight_trip">
                                                    <h3>Tunezja</h3>
                                                    <span className="trip-price"> od 1734 zł/os.</span>
                                                    <div className="dark_cover">
                                                        <div className="info-container">
                                                            <div className="trip_desc">
                                                                nieduży, ale bardzo gościnny i nadzwyczaj różnorodny kraj. Wielowiekowa historia, kolorowe gwarne bazary, strojne białe domki ukryte w jaśminie. Ale przede wszystkim cudowne plaże. Tunezja słynie z pięknych, szerokich, piaszczystych plaż, przez niektórych porównywanych wręcz do egzotycznych.
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div className="col-lg-4 col-md-6 trip_dir hiszpania">
                                            <div className="country-image-container">
                                                <img src={Spain} alt={""}/>
                                                <div className="highlight_trip">
                                                    <h3>Hiszpania</h3>
                                                    <span className="trip-price"> od 2193 zł/os.</span>
                                                    <div className="dark_cover">
                                                        <div className="info-container">
                                                            <div className="trip_desc">
                                                                kraj uwielbiany przez turystów z całego świata. Kochają ja i młodzi spragnieni wakacyjnego szaleństwa, ale i rodziny, które z dziećmi doskonale odnajdują się choćby w dzikich zatoczkach Costa Brava. Temperamentni, zawsze szczęśliwi Hiszpanie mogą stać się szkołą jak żyć a nie spieszyć się, może warto się tego od nich nauczyć?
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div className="col-lg-4 col-md-6 trip_dir grecja">
                                            <div className="country-image-container">
                                                <img src={Greece} alt={""}/>
                                                <div className="highlight_trip">
                                                    <h3>Grecja</h3>
                                                    <span className="trip-price"> od 1629 zł/os.</span>
                                                    <div className="dark_cover">
                                                        <div className="info-container">
                                                            <div className="trip_desc">
                                                                kraj słońca, gór, niekończących się plaż i lazurowego morza. Tu koimy zmysły,  delektujemy smakami wina, fety i oliwek a co i raz zaczepiani grecką serdecznością zatapiamy się w błogim wypoczynku. Grecja jest też swoistym powrotem do legend i mądrych słów wielkich Greków, w które po trosze wierzymy.
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div className="col-lg-4 col-md-6 trip_dir bulgaria">
                                            <div className="country-image-container">
                                                <img src={Bulgaria} alt={""}/>
                                                <div className="highlight_trip">
                                                    <h3>Bułgaria</h3>
                                                    <span className="trip-price"> od 1562 zł/os.</span>
                                                    <div className="dark_cover">
                                                        <div className="info-container">
                                                            <div className="trip_desc">
                                                                złoty kraj, który oferuje tak wiele za tak niewiele.  Począwszy od szerokich plaż, poprzez kameralne zatoczki, efektowne klify, przepiękne przełomy rzek o baśniowej urodzie, aż po majestatyczne góry i urzekające stare miasta.
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div className="col-lg-4 col-md-6 trip_dir chorwacja">
                                            <div className="country-image-container">
                                                <img src={Croatia} alt={""}/>
                                                <div className="highlight_trip">
                                                    <h3>Chorwacja</h3>
                                                    <span className="trip-price"> od 1892 zł/os.</span>
                                                    <div className="dark_cover">
                                                        <div className="info-container">
                                                            <div className="trip_desc">
                                                                perła Adriatyku, gdzie lato może być naprawdę gorąco, łatwo jednak odnaleźć nieco orzeźwienia przy wybrzeżu. Kusi bałkańską kuchnią oraz urzekającymi nadmorskimi miasteczkami. Chorwacja to świetne miejsce do wypoczynku z rodziną, ale nie tylko - mnogość opcji na rozrywkę i intensywne nocne życie jest naprawdę imponująca.
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </section>
                        </Container>
                        <section className='d-flex justify-content-center justify-content-lg-between p-2 '></section>
                        <Container>
                            <section className="r-container r-container--gray trip-info">
                                <div className="r-wrapper">

                                    <div className="trip-info-sections">
                                        <div className="trip-info-sect">
                                            <input type="checkbox" className="read-more-state" id="post-1"/>
                                            <h3>Last minute</h3>
                                            <p className="read-more-wrap">
                                                Jesteś ostatnio przemęczony i nie możesz się skupić na pracy, a aura za oknem sprawia, że najchętniej przez cały dzień nie wychodziłbyś spod kołdry? Zamiast zastanawiać się, jak przetrwać i nie zwariować sprawdź jaką ofertę last minute przygotowało nasze biuro podróży!
                                                <span className="read-more-target"> Jeśli tylko możesz na kilka dni oderwać się od obowiązków, to teraz jest najlepszy czas na podróż, ponieważ mamy wiele ciekawych destynacji w bardzo atrakcyjnych cenach. Może zainteresuje Cię Chorwacja, Grecja lub Portugalia? Tam zawsze masz gwarancję dobrej pogody, a niezliczone atrakcje turystyczne sprawią, że każdy dzień pobytu będzie pełen wrażeń. Dodaj do tego pyszne, lokalne jedzenie i otrzymasz definicję idealnego urlopu.
                                Oferta last minute jest świetnym rozwiązaniem dla osób podchodzących do życia spontanicznie, które nie lubią tworzyć długoterminowych planów.
                                    Czasami wystarczy tylko spakować kilka najpotrzebniejszych rzeczy i wyruszyć w podróż, która może stać się przygodą życia. Udany urlop wcale nie wymaga od Ciebie wielu miesięcy poszukiwań i zastanawiania się, gdzie i kiedy wyjechać. Od dawna marzysz o wyjeździe do Izraela czy Gruzji, ale zawsze się martwisz o finanse? Sprawdź naszą ofertę, bo teraz wyśnione wakacje są na wyciągnięcie ręki!
                                </span>
                                            </p>
                                            <label htmlFor="post-1" className="read-more-trigger"></label>
                                        </div>

                                        <div className="trip-info-sect">
                                            <input type="checkbox" className="read-more-state" id="post-2"/>
                                            <h3>All Inclusive</h3>
                                            <p className="read-more-wrap">
                                                Wyjazdy typu all inclusive są idealnym wyborem dla każdego turysty, zarówno dla singla podróżującego w pojedynkę, dużej rodziny z dziećmi, jak i dla par wybierających się na romantyczną wycieczkę we dwoje. Koszt takich wakacji, początkowo może wydawać się wysoki. <span className="read-more-target"> Jednak obejmuje on wszelkie wydatki - od podróży, przez pobyt w luksusowym hotelu, po wyżywienie, napoje i drinki. Pamiętaj, że nie ma żadnych międzynarodowych regulacji dokładnie określających, co wchodzi w zakres oferty all inclusive.
                                Niektóre hotele wprowadzają pewne ograniczenia, dlatego szczegóły musisz zawsze sprawdzić przy wyborze konkretnej oferty. Wybierając tę formę urlopu, masz jednak pewność, że nic nie zakłóci Twojego spokoju, nie będziesz musiał zaprzątać sobie głowy żadnymi organizacyjnymi szczegółami. Musisz jedynie wybrać wymarzoną destynację, określić swój budżet i cieszyć się wakacjami.
                                Nasze biuro podróży oferuje wyjazdy all inclusive do miejsc ulubionych przez wielu turystów, które od lat znajdują się w czołówce wybieranych kierunków, takich jak Grecja, Egipt, Chorwacja czy Tunezja. Możemy jednak zabrać Cię także w mniej oczywiste miejsca, między innymi do Albanii, Brazylii lub na Malediwy.
                               </span>
                                            </p>
                                            <label htmlFor="post-2" className="read-more-trigger"></label>
                                        </div>

                                        <div className="trip-info-sect">
                                            <input type="checkbox" className="read-more-state" id="post-3"/>
                                            <h3>Rejsy</h3>
                                            <p className="read-more-wrap">
                                                Rejsy statkiem są coraz popularniejszą formą spędzania wakacji. Dzięki takiej formie wczasów mamy możliwość odkryć piękno danych krajów z nieco innej strony. Otchłań morza, bezkres oceanu, a do tego cudowny wypoczynek w wygodnych kajutach, dobra zabawa, a także szeroka pula licznych atrakcji na statku
                                                <span className="read-more-target"> (basen, restauracje, miejsca zabaw dla dzieci) - to wszystko dostępne jest podczas wakacji w nieco innym wydaniu. Nie można zapominać o głównej atrakcji jakie mają do zaoferowania rejsy statkiem - zwiedzaniu i odkrywaniu piękna miast, do których codziennie przypływamy.
                                    Rejsy statkiem dostępne są także w formie wakacji all inclusive. Odkryj niezapomniane wakacje dla siebie i całej Twojej rodziny! Wybierz miejsce najlepsze dla siebie. Dzięki rejsom wycieczkowym odkryć można piękno świata w niecodziennej odsłonie. A może rejs po Nilu i poznanie tajemnic piramid w Gizie oraz Sfinksa?
                                    A może malownicze i zapierające dech w piersiach norweskich fiordach? Znikąd nie wyglądają tak pięknie, jak od strony wody! Rejsy statkiem to prawdziwy raj dla miłośników odkrywania świata! To forma wycieczek objazdowych, bez konieczności zmian noclegu i rozpakowywania się w nowych miejscach, gdzie zamiast autokaru czeka na nas luksusowy statek.
                                    </span>
                                            </p>
                                            <label htmlFor="post-3" className="read-more-trigger"></label>
                                        </div>

                                    </div>
                                </div>
                            </section>
                        </Container>
                    </header>
                    <Footer/>
                </main>
            )
        }
}

export default Home