-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Maj 12, 2024 at 06:32 PM
-- Wersja serwera: 10.4.28-MariaDB
-- Wersja PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gotravel`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `accommodation`
--

CREATE TABLE `accommodation` (
  `id_accommodation` int(11) NOT NULL,
  `name_accommodation` varchar(255) DEFAULT NULL,
  `price_accommodation` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `accommodation`
--

INSERT INTO `accommodation` (`id_accommodation`, `name_accommodation`, `price_accommodation`) VALUES
(1, 'fiveStarHotel', 500),
(2, 'fourStarHotel', 400),
(3, 'threeStarHotel', 300),
(4, 'twoStarHotel', 250),
(5, 'motel', 150);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `attractions`
--

CREATE TABLE `attractions` (
  `id_attraction` int(11) NOT NULL,
  `name_attraction` varchar(255) DEFAULT NULL,
  `price_attraction` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `attractions`
--

INSERT INTO `attractions` (`id_attraction`, `name_attraction`, `price_attraction`) VALUES
(1, 'sauna', 650),
(2, 'jacuzzi', 550),
(3, 'cityGuide', 750),
(4, 'cityTour', 680),
(5, 'hotelBar', 460),
(6, 'childrensAnimator', 600),
(7, 'childrensPlayroom', 700),
(8, 'bikeRental', 650),
(9, 'culinaryWorkshops', 960);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `attractions_own_trip`
--

CREATE TABLE `attractions_own_trip` (
  `id_own_offer` bigint(20) NOT NULL,
  `id_attraction` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `attractions_own_trip`
--

INSERT INTO `attractions_own_trip` (`id_own_offer`, `id_attraction`) VALUES
(2, 1),
(2, 3),
(3, 1),
(3, 3),
(4, 1),
(5, 1),
(6, 2),
(7, 1),
(8, 2),
(9, 2),
(10, 1),
(11, 1),
(12, 2),
(13, 1),
(14, 2),
(15, 1),
(15, 2),
(15, 4),
(15, 6),
(17, 1),
(17, 3),
(17, 6),
(17, 8),
(18, 1),
(19, 2),
(20, 1),
(20, 3),
(20, 5),
(21, 2),
(22, 2),
(22, 4),
(22, 6),
(22, 9);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `attractions_trips`
--

CREATE TABLE `attractions_trips` (
  `id_trip` bigint(20) NOT NULL,
  `id_attraction` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `attractions_trips`
--

INSERT INTO `attractions_trips` (`id_trip`, `id_attraction`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 2),
(2, 4),
(2, 5),
(2, 8),
(3, 3),
(6, 4),
(6, 6),
(17, 1),
(17, 7),
(22, 5),
(25, 1),
(29, 2),
(29, 4),
(40, 1),
(40, 4),
(40, 9),
(41, 1),
(41, 4),
(41, 9),
(42, 1),
(42, 4),
(42, 9),
(43, 1),
(43, 4),
(43, 9),
(44, 1),
(44, 4),
(44, 9),
(46, 1),
(46, 4),
(46, 9),
(47, 1),
(47, 4),
(47, 9),
(48, 1),
(48, 4),
(48, 9),
(49, 1),
(51, 1),
(51, 4),
(53, 5),
(53, 7),
(54, 3),
(54, 6),
(56, 2),
(56, 5),
(56, 6),
(58, 2),
(58, 5),
(58, 8),
(59, 5);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `cities`
--

CREATE TABLE `cities` (
  `id_city` int(11) NOT NULL,
  `name_city` varchar(255) DEFAULT NULL,
  `id_country` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cities`
--

INSERT INTO `cities` (`id_city`, `name_city`, `id_country`) VALUES
(1, 'Berat', 1),
(2, 'Butrint', 1),
(3, 'gjirokaster', 1),
(4, 'Korca', 1),
(5, 'Tirana', 1),
(6, 'vienna', 2),
(7, 'Innsbruck', 2),
(8, 'Graz', 2),
(9, 'Linz', 2),
(10, 'Salzburg', 2),
(11, 'brussels', 3),
(12, 'bruges', 3),
(13, 'ghent', 3),
(14, 'varna', 4),
(15, 'Sofia', 4),
(16, 'Nessebar', 4),
(17, 'dubrovnik', 5),
(18, 'Hvar', 5),
(19, 'Split', 5),
(20, 'zagreb', 5),
(21, 'prague', 6),
(22, 'Hrensko', 6),
(23, 'copenhagen', 7),
(24, 'Skagen', 7),
(25, 'tallinn', 8),
(26, 'Helsinki', 9),
(27, 'Oulu', 9),
(28, 'Rovaniemi', 9),
(29, 'paris', 10),
(30, 'marseille', 10),
(31, 'avignon', 10),
(32, 'Saint-Tropez', 10),
(33, 'athens', 11),
(34, 'rhodes', 11),
(35, 'Oia', 11),
(36, 'Barcelona', 12),
(37, 'sanSebastian', 12),
(38, 'cordoba', 12),
(39, 'Amsterdam', 13),
(40, 'zaanseSchans', 13),
(41, 'Dublin', 14),
(42, 'reykjavik', 15),
(43, 'vilnius', 16),
(44, 'Valletta', 17),
(45, 'Sliema', 17),
(46, 'monaco', 18),
(47, 'Berlin', 19),
(48, 'Hamburg', 19),
(49, 'munich', 19),
(50, 'Oslo', 20),
(51, 'Flam', 20),
(52, 'Sopot', 21),
(53, 'Zakopane', 21),
(54, 'zamosc', 21),
(55, 'gdansk', 21),
(56, 'Lisbon', 22),
(57, 'Carvoeiro', 22),
(58, 'Lagos', 22),
(59, 'bucharest', 23),
(60, 'sibiu', 23),
(61, 'sanMarino', 24),
(62, 'bratislava', 25),
(63, 'ljubljana', 26),
(64, 'Savica', 26),
(65, 'Lucerne', 27),
(66, 'stMoritz', 27),
(67, 'stockholm', 28),
(68, 'Ankara', 29),
(69, 'Antalya', 29),
(71, 'eph', 29),
(72, 'budapest', 30),
(73, 'Holloko', 30),
(74, 'london', 31),
(75, 'Brighton', 31),
(76, 'rome', 32),
(77, 'venice', 32),
(78, 'assisi', 32),
(79, 'Leuca', 32),
(80, 'nicosia', 33),
(81, 'paphos', 33);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `countries`
--

CREATE TABLE `countries` (
  `id_country` int(11) NOT NULL,
  `name_country` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `countries`
--

INSERT INTO `countries` (`id_country`, `name_country`) VALUES
(1, 'Albania'),
(2, 'Austria'),
(3, 'belgium'),
(4, 'bulgaria'),
(5, 'croatia'),
(6, 'czechRepublic'),
(7, 'denmark'),
(8, 'Estonia'),
(9, 'finland'),
(10, 'france'),
(11, 'greece'),
(12, 'spain'),
(13, 'netherlands'),
(14, 'ireland'),
(15, 'iceland'),
(16, 'lithuania'),
(17, 'Malta'),
(18, 'monaco'),
(19, 'germany'),
(20, 'norway'),
(21, 'poland'),
(22, 'portugal'),
(23, 'romania'),
(24, 'sanMarino'),
(25, 'slovakia'),
(26, 'slovenia'),
(27, 'switzerland'),
(28, 'sweden'),
(29, 'turkiye'),
(30, 'hungary'),
(31, 'greatBritain'),
(32, 'italy'),
(33, 'cyprus');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `favorite_trips`
--

CREATE TABLE `favorite_trips` (
  `id_favorite_trip` bigint(20) NOT NULL,
  `id_trip` bigint(20) DEFAULT NULL,
  `id_user` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `favorite_trips`
--

INSERT INTO `favorite_trips` (`id_favorite_trip`, `id_trip`, `id_user`) VALUES
(7, 2, 14),
(21, 6, 26),
(24, 8, 26),
(26, 6, 24);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `insurances`
--

CREATE TABLE `insurances` (
  `id_insurance` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `insurances`
--

INSERT INTO `insurances` (`id_insurance`, `name`, `price`) VALUES
(1, 'standard', 129.99),
(2, 'standardAndResignation', 189.99),
(3, 'optimumAndResignation', 249.99);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `opinions`
--

CREATE TABLE `opinions` (
  `id_opinion` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `id_trip` bigint(20) DEFAULT NULL,
  `id_user` bigint(20) DEFAULT NULL,
  `date_of_adding_the_opinion` date DEFAULT NULL,
  `stars` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `opinions`
--

INSERT INTO `opinions` (`id_opinion`, `description`, `id_trip`, `id_user`, `date_of_adding_the_opinion`, `stars`) VALUES
(1, 'fajna', 6, 8, '2024-02-04', 3.5),
(2, 'drftgrhjmkjhgf', 6, 6, '2023-08-22', 3),
(3, 'cdvbnmjuytrgfd', 6, 5, '2018-02-07', 1.5),
(5, 'fdsdc', 6, 5, '2024-02-16', 4.5),
(6, 'bla bla vla', 6, 5, '2024-02-16', 2),
(7, 'fd', 6, 5, '2024-02-16', 3.5),
(8, 'lkk', 6, 5, '2024-02-16', 4),
(11, 'mk', 6, 7, '2024-02-16', 3.5),
(15, 'tgtgt', 4, 24, '2024-04-07', 3),
(16, 'ghgn', 4, 24, '2024-04-07', 4),
(17, 'rrrrr', 4, 24, '2024-04-07', 2.5),
(18, 'yjuyj', 4, 24, '2024-04-07', 1),
(19, 'uyjuju', 4, 24, '2024-04-07', 4),
(20, 'uyjyj', 4, 24, '2024-04-07', 2.5),
(25, 'hjh', 1, 26, '2024-04-10', 3.5),
(26, 'dddwe', 1, 26, '2024-04-10', 2.5),
(27, 'kiii', 1, 26, '2024-04-10', 4.5);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `own_offer`
--

CREATE TABLE `own_offer` (
  `id_own_offer` bigint(20) NOT NULL,
  `date_of_reservation` date DEFAULT NULL,
  `departure_date` date DEFAULT NULL,
  `food` varchar(255) DEFAULT NULL,
  `number_of_adults` int(11) NOT NULL,
  `number_of_children` int(11) NOT NULL,
  `number_of_days` int(11) NOT NULL,
  `total_price` double NOT NULL,
  `id_accommodation` int(11) DEFAULT NULL,
  `id_city` int(11) DEFAULT NULL,
  `id_user` bigint(20) DEFAULT NULL,
  `payment` bit(1) NOT NULL,
  `id_insurance` int(11) DEFAULT NULL,
  `accepted` bit(1) NOT NULL,
  `changed_acceptance_state` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `own_offer`
--

INSERT INTO `own_offer` (`id_own_offer`, `date_of_reservation`, `departure_date`, `food`, `number_of_adults`, `number_of_children`, `number_of_days`, `total_price`, `id_accommodation`, `id_city`, `id_user`, `payment`, `id_insurance`, `accepted`, `changed_acceptance_state`) VALUES
(2, '2024-02-13', '2024-02-14', '1', 1, 1, 3, 9500, 1, 12, 7, b'0', 1, b'0', b'0'),
(3, '2024-02-13', '2024-02-13', '1', 1, 1, 3, 9500, 1, 11, 7, b'0', 2, b'0', b'0'),
(4, '2024-02-13', '2024-02-16', '1', 1, 0, 7, 8070, 1, 22, 7, b'0', 3, b'0', b'0'),
(5, '2024-02-13', '2024-02-13', '0', 1, 1, 2, 2170, 2, 6, 7, b'0', 2, b'0', b'0'),
(6, '2024-02-13', '2024-02-13', '0', 1, 0, 1, 1210, 3, 7, 7, b'0', 1, b'0', b'0'),
(7, '2024-02-13', '2024-02-13', '0', 1, 0, 1, 1230, 2, 6, 7, b'0', 3, b'0', b'0'),
(8, '2024-02-13', '2024-02-13', '0', 1, 0, 1, 1130, 2, 7, 7, b'0', 1, b'0', b'0'),
(9, '2024-02-13', '2024-02-13', '0', 1, 0, 1, 1160, 4, 12, 7, b'0', 2, b'0', b'0'),
(10, '2024-02-13', '2024-02-13', '0', 1, 0, 1, 1410, 2, 11, 7, b'0', 1, b'0', b'0'),
(11, '2024-02-13', '2024-02-13', '0', 1, 0, 1, 1970, 3, 11, 7, b'0', 1, b'0', b'0'),
(12, '2024-02-13', '2024-02-13', '0', 1, 0, 1, 1030, 3, 16, 7, b'0', 3, b'0', b'0'),
(13, '2024-02-13', '2024-02-13', '0', 1, 0, 1, 1310, 3, 16, 7, b'0', 2, b'0', b'0'),
(14, '2024-02-14', '2024-02-14', '0', 1, 0, 1, 1130, 2, 12, 7, b'1', 1, b'0', b'0'),
(15, '2024-03-01', '2024-03-01', '1', 1, 0, 2, 4040, 2, 16, 24, b'0', 3, b'0', b'0'),
(17, '2024-04-25', '2024-04-24', '1', 2, 1, 3, 12980, 1, 15, 24, b'0', 2, b'1', b'1'),
(18, '2024-04-25', '2024-04-19', '1', 1, 0, 3, 2540, 4, 18, 24, b'1', 3, b'0', b'1'),
(19, '2024-03-08', '2024-03-13', '0', 2, 0, 1, 2070, 2, 38, 24, b'0', 2, b'0', b'0'),
(20, '2024-03-12', '2024-03-13', '1', 2, 1, 1, 4470, 2, 15, 24, b'1', 2, b'0', b'0'),
(21, '2024-04-21', '2024-04-19', '1', 1, 0, 2, 2110, 2, 74, 24, b'1', 3, b'0', b'0'),
(22, '2024-04-10', '2024-04-17', '1', 2, 2, 4, 18950, 2, 15, 24, b'1', 1, b'1', b'1');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `own_offer_type_of_room`
--

CREATE TABLE `own_offer_type_of_room` (
  `id_own_offer_type_of_room` int(11) NOT NULL,
  `number_of_room` int(11) NOT NULL,
  `id_own_offer` bigint(20) DEFAULT NULL,
  `id_type_of_room` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `own_offer_type_of_room`
--

INSERT INTO `own_offer_type_of_room` (`id_own_offer_type_of_room`, `number_of_room`, `id_own_offer`, `id_type_of_room`) VALUES
(1, 1, 2, 1),
(2, 2, 2, 2),
(3, 1, 3, 1),
(4, 2, 3, 2),
(5, 1, 4, 2),
(6, 1, 5, 2),
(7, 1, 6, 2),
(8, 1, 7, 1),
(9, 1, 8, 1),
(10, 1, 9, 2),
(11, 1, 10, 2),
(12, 2, 11, 2),
(13, 1, 12, 1),
(14, 1, 13, 3),
(15, 1, 14, 1),
(16, 1, 15, 1),
(18, 1, 17, 4),
(19, 2, 17, 3),
(20, 1, 18, 1),
(21, 2, 19, 3),
(22, 1, 20, 3),
(23, 1, 20, 5),
(24, 1, 21, 1),
(25, 2, 22, 1),
(26, 3, 22, 2);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `photos`
--

CREATE TABLE `photos` (
  `id_photo` bigint(20) NOT NULL,
  `url_photo` varchar(255) DEFAULT NULL,
  `id_trip` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `photos`
--

INSERT INTO `photos` (`id_photo`, `url_photo`, `id_trip`) VALUES
(1, 'https://i.wpimg.pl/O/1000x683/d.wpimg.pl/907326924-922629792/ateny_Anastasios71%20shutterstock_118048216.jpg', 1),
(2, 'https://fly.pl/wp-content/uploads/2019/07/Rodos-Kallithea_-Zatoka-Antonnego-Quinna.jpg', 2),
(3, 'https://www.wiecznatulaczka.pl/wp-content/uploads/2015/03/Czy-Zakopane-jest-%C5%82adne1.jpg', 3),
(4, 'https://www.uniqa.pl/files/_processed_/0/6/csm_planowanie-wakacji_7a8751987e.jpg', 4),
(5, 'https://arc.com.pl/wp-content/uploads/2021/06/Plaz%CC%87a_ok-940x675.png', 5),
(6, 'https://www.fjords.com/wp-content/uploads/2022/08/oslo_oslofjord_lambda_opera_munch_fjords_norway-2000x1200.jpg', 6),
(7, 'https://www.countryandtownhouse.com/wp-content/uploads/2023/02/GettyImages-1357022260.jpg', 7),
(8, 'https://travelstory.pl/wp-content/uploads/2021/07/travelstory.pl-zakopane-3.jpg', 8),
(9, 'https://res.cloudinary.com/gofjords-com/image/upload/q_auto:low,f_auto,c_fill,w_2560/v1607338606/Blog%20Content/Bergen/Get%20the%20most%20out%20of%20Flam%20in%20a%20short%20time/Stegastein-viewpoint-Aurlandsfjord-Flam-1.jpg', 9),
(10, 'https://media.travelbay.pl/images/europa/hiszpania/kordoba/kordoba-2.jpg?tr=n-open_graph', 10),
(11, 'https://media.istockphoto.com/id/538600418/pl/zdj%C4%99cie/rzeki-vltava-rzeka-i-most-charle-z-czerwonej-li%C5%9Bcie.jpg?s=612x612&w=0&k=20&c=094e6vNYgQPyeVSLlgakEMLWnSCinOyrF_D_HlwjOe8=', 11),
(12, 'https://www.uniqa.pl/files/_processed_/0/6/csm_planowanie-wakacji_7a8751987e.jpg', 12),
(13, 'https://arc.com.pl/wp-content/uploads/2021/06/Plaz%CC%87a_ok-940x675.png', 9),
(14, 'https://wakacyjnapolisa.pl/wp-content/uploads/2015/05/youth-570881-1024x682.jpg', 10),
(15, 'https://cdn.galleries.smcloud.net/t/galleries/gf-prA5-Rpp6-Smxr_wakacje-1920x1080-nocrop.jpg', 11),
(16, 'https://www.rego-bis.pl/upload/editor/phpmrej43.jpeg', 12),
(17, 'https://www.travelplanet.pl/dbphotos/przewodnik/ateny_2241.jpg', 1),
(18, 'https://wf1.xcdn.pl/files/21/08/26/973730_oliP_acropolis12044_83.jpg.webp', 1),
(19, 'https://www.traveligo.pl/zdjecia/Grecja/Ateny/Zakochani_w_Atenach/Zakochani_w_Atenach_Ateny_Grecja-e193f23dc0c1fe61f98c6127978eeecd.webp', 1),
(20, 'https://www.momondo.pl/rimg/himg/d8/cd/8b/expediav2-168072-3637658627-968984.jpg?width=720&height=576&crop=true', 2),
(21, 'https://media.travelbay.pl/images/europa/francja/saint-tropez/saint-tropez-2.jpeg?tr=n-hero', 21),
(22, 'https://norwegiairesztaświata.pl/wp-content/uploads/2022/10/DSC_0179-02-1140x759.jpeg', 22),
(23, 'https://media.travelbay.pl/images/europa/dania/skagen/skagen-4.jpg', 23),
(24, 'https://app.camprest.com/BlogPost/Content%202022/Turystyka/Belgia/Projekt%20bez%20tytu%C5%82u%20-%202022-08-18T113112.796.png', 24),
(25, 'https://www.gdzie-i-kiedy.pl/site/images/illustration/sliema.jpg', 25),
(26, 'https://f4fcdn.eu/wp-content/uploads/2018/10/Brugia2000ST.jpg', 26),
(27, 'https://www.travelsicht.de/wp-content/uploads/2023/02/korca-1180x885.jpg', 27),
(28, 'https://podroztrwa.pl/wp-content/uploads/2021/04/marseille-4615791_1920-min.jpg', 28),
(29, 'https://henriwillig.com/site-henriwillig/assets/files/1112/catharinahoeve.jpg', 29),
(30, 'https://i.wpimg.pl/O/860x493/d.wpimg.pl/900651685--1872249437/shutterstock_300856853.jpg', 30),
(31, 'https://www.grecos.pl/-/media/grecos/przewodniki/rodos/grecos-rodos-007.ashx', 31),
(32, 'https://i.iplsc.com/brighton-beach/00062SXY4MT9VOPK-C122-F4.jpg', 32),
(33, 'https://media.travelbay.pl/images/europa/hiszpania/kordoba/kordoba-2.jpg?tr=n-open_graph', 33),
(34, 'https://dcontent.inviacdn.net/shared/img/web-654x382/2020/9/2/d1/26546767.jpg', 2),
(35, 'https://www.decormint.com/storage/thumbs/fotolia/126086896/thumbnw_3d940df66a8bfa4bf4b26f0f1c5c186d.jpg', 3),
(36, 'https://www.national-geographic.pl/media/cache/big/uploads/media/default/0014/53/ateny-kolebka-cywilizacji-i-demokracji-co-warto-zobaczyc-w-stolicy-grecji.jpeg', 4),
(37, 'https://dziendobry.tvn.pl/najnowsze/cdn-zdjecie-ou0rvr-wilno-5468941/alternates/LANDSCAPE_1280', 5),
(38, 'https://zamkiobronne.pl/wp-content/uploads/2017/09/holloko03.jpg', 15),
(39, 'https://www.wiecznatulaczka.pl/wp-content/uploads/2015/03/Czy-Zakopane-jest-%C5%82adne1.jpg', 8),
(40, 'https://wiedniu.pl/wp-content/uploads/2022/02/wieden.jpg', 16),
(41, 'https://www.stenaline.pl/content/dam/stenaline/pl/content-fragments/destinations/sweden/destination/stockholm/_jcr_content/data/master.cfimg.85.1024.fileReference.jpeg/1636965540549/20190603-stockholm-old-town.jpeg', 14),
(42, 'https://media.istockphoto.com/id/476653220/pl/zdj%C4%99cie/ghent.jpg?s=612x612&w=0&k=20&c=s8pExwpPI12GzyXoWMkmGbBzAJsJpPNS-OiawrbLSvc=', 13),
(43, 'https://balkany.pl/wp-content/uploads/2021/10/split-nabrzeze-i-widok-z-lotu-ptaka-marjan-dalmacja-chorwacj_Split-shutterstock_311158274-scaled.jpg', 19),
(44, 'https://i.wpimg.pl/1200x/i.wp.pl/a/f/jpeg/36675/dubrownik_sorin_colac_shutterstock.jpeg', 18),
(45, 'https://naszepodroze.edu.pl/wp-content/uploads/2020/03/stolica-Chorwacji-1140x757.jpg', 17),
(46, 'https://i.wpimg.pl/1200x/i.wp.pl/a/f/jpeg/35742/zamosc900_nightman1965_shutterstock.jpeg', 34),
(54, 'https://ocdn.eu/pulscms-transforms/1/yERk9kpTURBXy82YWEwZWFmMzQ0YWQzNmQ5ZTk2MDc2MTIwZTJiMWUwNy5qcGeTlQMAzHfND6DNCMqVAs0EsADDw5MJpmZkNDM5MwbeAAGhMAE/rysy.jpeg', 47),
(55, 'https://ocdn.eu/pulscms-transforms/1/yERk9kpTURBXy82YWEwZWFmMzQ0YWQzNmQ5ZTk2MDc2MTIwZTJiMWUwNy5qcGeTlQMAzHfND6DNCMqVAs0EsADDw5MJpmZkNDM5MwbeAAGhMAE/rysy.jpeg', 48),
(61, 'https://1.bp.blogspot.com/-O5dxPSw-z5I/T56EjjoMYnI/AAAAAAAAHr0/XNInjmUb1L4/s1600/100_1187.JPG', 53),
(62, 'https://1.bp.blogspot.com/-O5dxPSw-z5I/T56EjjoMYnI/AAAAAAAAHr0/XNInjmUb1L4/s1600/100_1187.JPG', 54),
(74, 'https://1.bp.blogspot.com/-O5dxPSw-z5I/T56EjjoMYnI/AAAAAAAAHr0/XNInjmUb1L4/s1600/100_1187.JPG', 56),
(78, 'https://1.bp.blogspot.com/-O5dxPSw-z5I/T56EjjoMYnI/AAAAAAAAHr0/XNInjmUb1L4/s1600/100_1187.JPG', 51),
(79, 'https://ecowater.pl/assets/BlogsImages/AdobeStock_413608281__FillMaxWzc1MCw0MjJd.jpeg', 58),
(80, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmr0s-cgyVq8MzmC59M3fqbjFQHVBQTMKJvgCANyGKJQ&s', 58),
(81, 'https://zdrojowahotels.pl/media/639c68d18a86253dbf2b9ae2', 59);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `refreshtoken`
--

CREATE TABLE `refreshtoken` (
  `id` bigint(20) NOT NULL,
  `expiry_date` datetime NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `refreshtoken`
--

INSERT INTO `refreshtoken` (`id`, `expiry_date`, `token`, `user_id`) VALUES
(2252, '2024-02-19 20:04:49', '14d90b54-c88c-4898-95d5-b532efbf6e0d', 15),
(2302, '2024-02-19 20:23:21', 'bcb5e870-f101-48d1-a1c5-74d2ba0e2b3a', 17),
(2352, '2024-02-19 20:36:02', 'dc0b6053-9dae-472f-a883-cf46fa24ddcc', 18),
(4302, '2024-04-12 23:25:39', '92412a0a-7b1d-478e-be48-cdc38d956a8a', 25),
(4402, '2024-05-08 09:47:28', '487a54d5-b087-425d-8b65-933fdded621a', 24);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `refreshtoken_seq`
--

CREATE TABLE `refreshtoken_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `refreshtoken_seq`
--

INSERT INTO `refreshtoken_seq` (`next_val`) VALUES
(4501);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `reservations`
--

CREATE TABLE `reservations` (
  `id_reservation` bigint(20) NOT NULL,
  `date_of_reservation` date DEFAULT NULL,
  `departure_date` date DEFAULT NULL,
  `number_of_adults` int(11) NOT NULL,
  `number_of_children` int(11) NOT NULL,
  `total_price` double NOT NULL,
  `id_trip` bigint(20) DEFAULT NULL,
  `id_user` bigint(20) DEFAULT NULL,
  `payment` bit(1) NOT NULL,
  `id_insurance` int(11) DEFAULT NULL,
  `accepted` bit(1) NOT NULL,
  `changed_acceptance_state` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservations`
--

INSERT INTO `reservations` (`id_reservation`, `date_of_reservation`, `departure_date`, `number_of_adults`, `number_of_children`, `total_price`, `id_trip`, `id_user`, `payment`, `id_insurance`, `accepted`, `changed_acceptance_state`) VALUES
(9, '2024-02-12', '2024-02-12', 1, 0, 1022, 6, 5, b'0', 1, b'0', b'0'),
(10, '2024-02-12', '2024-02-12', 1, 0, 1022, 6, 5, b'0', 2, b'0', b'0'),
(11, '2024-02-12', '2024-02-12', 1, 0, 1500, 1, 5, b'0', 3, b'0', b'0'),
(12, '2024-02-12', '2024-02-12', 1, 0, 1500, 1, 5, b'0', 1, b'0', b'0'),
(13, '2024-02-12', '2024-02-12', 1, 0, 1022, 6, 5, b'0', 2, b'0', b'0'),
(14, '2024-02-12', '2024-02-16', 1, 1, 1533, 6, 5, b'0', 3, b'0', b'0'),
(15, '2024-02-12', '2024-02-28', 1, 0, 1022, 6, 5, b'0', 1, b'0', b'0'),
(16, '2024-02-12', '2024-03-04', 1, 0, 1022, 6, 5, b'0', 2, b'0', b'0'),
(17, '2024-02-12', '2024-02-12', 1, 0, 1022, 6, 5, b'0', 3, b'0', b'0'),
(18, '2024-02-12', '2024-02-12', 1, 0, 1022, 6, 5, b'0', 3, b'0', b'0'),
(19, '2024-02-14', '2024-02-14', 3, 0, 3066, 6, 7, b'0', 2, b'0', b'0'),
(20, '2024-02-14', '2024-02-14', 3, 0, 3066, 6, 7, b'0', 2, b'0', b'0'),
(21, '2024-02-14', '2024-02-14', 2, 0, 1306, 7, 7, b'0', 2, b'0', b'0'),
(22, '2024-02-14', '2024-02-14', 2, 0, 1306, 7, 7, b'1', 3, b'0', b'0'),
(23, '2024-02-14', '2024-02-14', 1, 0, 1022, 6, 7, b'1', 1, b'0', b'0'),
(24, '2024-02-14', '2024-02-14', 1, 0, 1500, 1, 7, b'1', 2, b'0', b'0'),
(25, '2024-02-14', '2024-02-14', 1, 0, 1022, 6, 7, b'1', 2, b'0', b'0'),
(26, '2024-03-01', '2024-03-01', 1, 0, 1022, 6, 5, b'0', 2, b'0', b'0'),
(27, '2024-03-01', '2024-03-01', 1, 0, 1700, 3, 24, b'0', 1, b'0', b'0'),
(30, '2024-03-04', '2024-03-03', 2, 0, 3000, 1, 24, b'1', 3, b'0', b'0'),
(32, '2024-03-04', '2024-03-29', 1, 0, 1700, 3, 24, b'0', 3, b'0', b'1'),
(33, '2024-03-04', '2024-03-26', 2, 1, 3750, 1, 24, b'0', 3, b'0', b'0'),
(34, '2024-03-08', '2024-03-09', 1, 1, 2400, 32, 24, b'1', 2, b'0', b'0'),
(35, '2024-03-08', '2024-03-22', 1, 0, 123.9, 34, 24, b'1', 1, b'0', b'0'),
(36, '2024-03-08', '2024-03-27', 1, 1, 979.5, 7, 24, b'0', 3, b'1', b'1'),
(37, '2024-04-09', '2024-04-09', 2, 2, 5880, 6, 24, b'0', 2, b'1', b'1'),
(38, '2024-04-09', '2024-04-09', 2, 2, 5880, 6, 24, b'1', 2, b'1', b'1'),
(40, '2024-04-10', '2024-04-15', 2, 0, 3920, 6, 26, b'1', 2, b'1', b'1'),
(41, '2024-04-11', '2024-04-17', 2, 0, 3920, 6, 24, b'0', 2, b'0', b'1');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `reservations_type_of_room`
--

CREATE TABLE `reservations_type_of_room` (
  `id_reservations_type_of_room` int(11) NOT NULL,
  `number_of_room` int(11) NOT NULL,
  `id_reservation` bigint(20) DEFAULT NULL,
  `id_type_of_room` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservations_type_of_room`
--

INSERT INTO `reservations_type_of_room` (`id_reservations_type_of_room`, `number_of_room`, `id_reservation`, `id_type_of_room`) VALUES
(6, 1, 10, 5),
(7, 1, 11, 4),
(8, 2, 11, 3),
(9, 1, 12, NULL),
(10, 0, 13, 2),
(11, 1, 14, 1),
(12, 1, 15, 1),
(13, 1, 16, 4),
(14, 1, 17, 1),
(15, 1, 18, 3),
(16, 3, 19, 2),
(17, 3, 20, 1),
(18, 2, 21, 2),
(19, 2, 22, 3),
(20, 1, 23, 2),
(21, 1, 24, 2),
(22, 1, 25, 1),
(23, 1, 26, 1),
(24, 2, 27, 2),
(27, 3, 30, 4),
(29, 1, 32, 1),
(30, 1, 33, 2),
(31, 2, 34, 2),
(32, 1, 34, 5),
(33, 2, 34, 3),
(34, 2, 35, 2),
(35, 2, 36, 2),
(36, 2, 38, 1),
(38, 2, 41, 1),
(39, 2, 41, 4);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `roles`
--

CREATE TABLE `roles` (
  `id_role` bigint(20) NOT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id_role`, `name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_MODERATOR'),
(3, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `transports`
--

CREATE TABLE `transports` (
  `id_transport` int(11) NOT NULL,
  `name_transport` varchar(255) DEFAULT NULL,
  `price_transport` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transports`
--

INSERT INTO `transports` (`id_transport`, `name_transport`, `price_transport`) VALUES
(1, 'plane', 500),
(2, 'bus', 100),
(3, 'boat', 370),
(4, 'ship', 679),
(5, 'ferry', 720);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `trips`
--

CREATE TABLE `trips` (
  `id_trip` bigint(20) NOT NULL,
  `food` varchar(255) DEFAULT NULL,
  `number_of_days` int(11) NOT NULL,
  `price` double NOT NULL,
  `representative_photo` varchar(255) DEFAULT NULL,
  `trip_description` longtext DEFAULT NULL,
  `id_accommodation` int(11) DEFAULT NULL,
  `id_city` int(11) DEFAULT NULL,
  `id_transport` int(11) DEFAULT NULL,
  `id_type_of_trip` bigint(20) DEFAULT NULL,
  `activity_level` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `trips`
--

INSERT INTO `trips` (`id_trip`, `food`, `number_of_days`, `price`, `representative_photo`, `trip_description`, `id_accommodation`, `id_city`, `id_transport`, `id_type_of_trip`, `activity_level`) VALUES
(1, 'breakfastSupper', 2, 1300, 'https://i.wpimg.pl/O/1000x683/d.wpimg.pl/907326924-922629792/ateny_Anastasios71%20shutterstock_118048216.jpg', 'Odkryj piękno piaszczystych plaż i ciepłego morza. Ciesz się porannym śniadaniem z widokiem na ocean, a potem relaksuj się na plaży lub wybierz się na spokojne pływanie żaglówką. Odkryj podwodny świat podczas nurkowania w krystalicznie czystej wodzie.', 1, 33, 2, 1, 0.5),
(2, 'breakfast', 1, 1400, 'https://fly.pl/wp-content/uploads/2019/07/Rodos-Kallithea_-Zatoka-Antonnego-Quinna.jpg', 'Wyspa Rodos to wakacyjny raj. Przemierzając wyspę wzdłuż i wszerz, można poczuć prawdziwą wolność i pełnię szczęścia, gdy wiatr tańczy we włosach, a powietrze przesycone jest aromatem ziół porastających górskie zbocza. By ułatwić wybór najlepszych hoteli przygotowaliśmy listę polecanych obiektów. Ciesz się weekendem na plaży z możliwością surfingu i kajakarstwa. Rano rozkoszuj się śniadaniem z widokiem na spokojne fale, a wieczorem odpocznij przy dźwiękach oceanu.', 2, 34, 1, 1, 0.8),
(3, 'breakfastDinner', 2, 1450, 'https://cdn.getyourguide.com/img/tour/610babd8e1b07.jpeg/146.jpg', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie. Odwiedź zatokę pełną życia morskiego i piaszczystych plaż. Twoja przygoda rozpocznie się od śniadania na plaży, a potem możesz wybrać się na łagodne windsurfing lub relaksujące pływanie w ciepłych wodach.', 2, 35, 1, 1, 1),
(4, 'breakfast', 1, 1510, 'https://www.national-geographic.pl/media/cache/big/uploads/media/default/0014/53/ateny-kolebka-cywilizacji-i-demokracji-co-warto-zobaczyc-w-stolicy-grecji.jpeg', 'Idealna ucieczka na weekend do kamienistej zatoki z możliwością snorkelingu przy rafie koralowej. Zacznij dzień od pysznego śniadania z widokiem na morze, a potem ciesz się spokojem i ciszą otoczenia.', 2, 33, 2, 1, 0.3),
(5, 'breakfastSupper', 2, 1490, 'https://dziendobry.tvn.pl/najnowsze/cdn-zdjecie-ou0rvr-wilno-5468941/alternates/LANDSCAPE_1280', 'Spędź niezapomniany czas na plażowym wyjeździe, gdzie możesz cieszyć się pływaniem, żeglowaniem i nurkowaniem. Rozpocznij każdy dzień od śniadania na plaży, relaksując się przy dźwięku fal.', 2, 43, 4, 1, 0.9),
(6, 'breakfast', 3, 1960, 'https://www.fjords.com/wp-content/uploads/2022/08/oslo_oslofjord_lambda_opera_munch_fjords_norway-2000x1200.jpg', 'Zapraszamy na trzydniową przygodę w górach, gdzie każdy dzień rozpoczyna się od energetyzującego śniadania, które przygotuje Cię do wspinaczki lub trekkingu. Poznaj nieznane szlaki, podziwiaj zapierające dech w piersiach widoki z najwyższych szczytów i odkryj ukryte jaskinie. Po dniu pełnym aktywności, wracaj do przytulnego schroniska, by odpocząć i nabrać sił na kolejne wyprawy.', 3, 50, 1, 2, 0.8),
(7, 'breakfastSupper', 4, 1800, 'https://www.countryandtownhouse.com/wp-content/uploads/2023/02/GettyImages-1357022260.jpg', 'Poddaj się urokowi górskich szczytów z tym czterodniowym pakietem. Rozpocznij każdy dzień od śniadania z widokiem na pasmo górskie, a wieczory spędzaj przy kolacji podziwiając gwiaździste niebo. Dzień możesz spędzić na trekkingu po malowniczych szlakach lub relaksować się w pobliskich źródłach termalnych. Schronisko górskie zapewni komfortowy wypoczynek i niezapomniane wspomnienia.', 2, 10, 3, 2, 0.9),
(8, 'breakfastSupper', 5, 2500, 'https://www.wiecznatulaczka.pl/wp-content/uploads/2015/03/Czy-Zakopane-jest-%C5%82adne1.jpg', 'Ta pięciodniowa wycieczka to idealna okazja, aby naładować baterie w otoczeniu górskiego krajobrazu. Ciesz się kolacjami przy świecach z lokalnych produktów po całym dniu spędzonym na odkrywaniu hal i klifów. Pozwól sobie na odrobinę luksusu w schronisku górskim, gdzie każdy wieczór to okazja do relaksu i podziwiania gwiazd. Dni pełne są możliwości do trekkingu, wspinaczki, a nawet alpinizmu dla chętnych.', 1, 53, 2, 2, 1),
(9, 'breakfastSupper', 4, 3000, 'https://res.cloudinary.com/gofjords-com/image/upload/q_auto:low,f_auto,c_fill,w_2560/v1607338606/Blog%20Content/Bergen/Get%20the%20most%20out%20of%20Flam%20in%20a%20short%20time/Stegastein-viewpoint-Aurlandsfjord-Flam-1.jpg', 'Odkryj piękno górskich szlaków podczas czterodniowego pobytu. Dni zaczynają się od pożywnego śniadania, które doda Ci energii do eksploracji górskich przełęczy i szczytów. Wieczory to czas na wspólne kolacje i wymianę doświadczeń z innymi podróżnikami. Wycieczka obejmuje trekking po malowniczych trasach, możliwość wspinaczki i odwiedziny w schroniskach, gdzie można się ogrzać i zjeść domowe posiłki.', 1, 51, 2, 7, 0.5),
(10, 'breakfastSupper', 3, 1200, 'https://images.musement.com/cover/0079/43/thumb_7842996_cover_header.jpeg', 'Spędź trzy niezapomniane dni w sercu gór, rozpoczynając każdy poranek od śniadania z widokiem na śnieżne szczyty. W ciągu dnia czekają na Ciebie emocjonujące trasy trekkingowe, a wieczór to czas na odpoczynek i kolacje w górskim schronisku. Daj się ponieść przygodzie i odkryj sekrety górskich szlaków, kaskad i pasm. To wyjątkowa okazja do doświadczenia ciszy i spokoju, jakie oferują tylko góry.\"', 4, 40, 5, 7, 0.7),
(11, 'breakfastSupper', 12, 1200, 'https://media.istockphoto.com/id/538600418/pl/zdj%C4%99cie/rzeki-vltava-rzeka-i-most-charle-z-czerwonej-li%C5%9Bcie.jpg?s=612x612&w=0&k=20&c=094e6vNYgQPyeVSLlgakEMLWnSCinOyrF_D_HlwjOe8=', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 3, 21, 3, 3, 5),
(12, 'breakfastDinner', 20, 1800, 'https://www.rego-bis.pl/upload/editor/phpmrej43.jpeg', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 2, 1, 1, 3, 10),
(13, 'breakfastSupper', 10, 122, 'https://media.istockphoto.com/id/476653220/pl/zdj%C4%99cie/ghent.jpg?s=612x612&w=0&k=20&c=s8pExwpPI12GzyXoWMkmGbBzAJsJpPNS-OiawrbLSvc=', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 1, 13, 4, 4, 3),
(14, 'breakfastDinnerSupper', 21, 123, 'https://www.stenaline.pl/content/dam/stenaline/pl/content-fragments/destinations/sweden/destination/stockholm/_jcr_content/data/master.cfimg.85.1024.fileReference.jpeg/1636965540549/20190603-stockholm-old-town.jpeg', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 2, 67, 3, 4, 10),
(15, 'breakfastSupper', 12, 122, 'https://zamkiobronne.pl/wp-content/uploads/2017/09/holloko03.jpg', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 2, 73, 4, 2, 5),
(16, 'breakfastSupper', 20, 12229, 'https://wiedniu.pl/wp-content/uploads/2022/02/wieden.jpg', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 4, 6, 3, 4, 10),
(17, 'breakfastSupper', 10, 1900, 'https://naszepodroze.edu.pl/wp-content/uploads/2020/03/stolica-Chorwacji-1140x757.jpg', 'Po górach spacerowanie, trekking fajny, zdybywanie szczytów.', 1, 20, 1, 5, 7),
(18, 'breakfastDinnerSupper', 12, 875, 'https://i.wpimg.pl/1200x/i.wp.pl/a/f/jpeg/36675/dubrownik_sorin_colac_shutterstock.jpeg', 'wakacje promocj3r3werererere', 4, 17, 4, 5, 9),
(19, 'dinnerSupper', 12, 1220, 'https://balkany.pl/wp-content/uploads/2021/10/split-nabrzeze-i-widok-z-lotu-ptaka-marjan-dalmacja-chorwacj_Split-shutterstock_311158274-scaled.jpg', 'allInclusive promocj3rrrrr3erere', 3, 19, 3, 5, 8),
(21, 'dinnerSupper', 10, 1400, 'https://media.travelbay.pl/images/europa/francja/saint-tropez/saint-tropez-2.jpeg?tr=n-hero', 'wakacje promocj3r3erere', 2, 32, 2, 5, 0),
(22, 'dinnerSupper', 10, 122, 'https://norwegiairesztaświata.pl/wp-content/uploads/2022/10/DSC_0179-02-1140x759.jpeg', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 3, 67, 2, 6, 3),
(23, 'breakfastDinnerSupper', 21, 6503, 'https://media.travelbay.pl/images/europa/dania/skagen/skagen-4.jpg', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 2, 24, 4, 6, 9),
(24, 'dinnerSupper', 4, 122, 'https://app.camprest.com/BlogPost/Content%202022/Turystyka/Belgia/Projekt%20bez%20tytu%C5%82u%20-%202022-08-18T113112.796.png', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 4, 11, 4, 2, 1),
(25, 'dinnerSupper', 20, 1222, 'https://www.gdzie-i-kiedy.pl/site/images/illustration/sliema.jpg', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 1, 45, 3, 6, 10),
(26, 'dinnerSupper', 3, 122, 'https://f4fcdn.eu/wp-content/uploads/2018/10/Brugia2000ST.jpg', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 1, 12, 2, 7, 0),
(27, 'breakfastDinnerSupper', 5, 680, 'https://www.travelsicht.de/wp-content/uploads/2023/02/korca-1180x885.jpg', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 4, 4, 3, 7, 2),
(28, 'dinnerSupper', 6, 1220, 'https://podroztrwa.pl/wp-content/uploads/2021/04/marseille-4615791_1920-min.jpg', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 3, 30, 1, 7, 6),
(29, 'dinnerSupper', 4, 1400, 'https://henriwillig.com/site-henriwillig/assets/files/1112/catharinahoeve.jpg', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 2, 40, 4, 7, 1),
(30, 'dinnerSupper', 10, 1850, 'https://i.wpimg.pl/O/860x493/d.wpimg.pl/900651685--1872249437/shutterstock_300856853.jpg', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 3, 21, 2, 8, 0),
(31, 'breakfastDinnerSupper', 13, 6503, 'https://www.grecos.pl/-/media/grecos/przewodniki/rodos/grecos-rodos-007.ashx', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 1, 34, 3, 8, 8),
(32, 'dinnerSupper', 12, 1600, 'https://i.iplsc.com/brighton-beach/00062SXY4MT9VOPK-C122-F4.jpg', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 4, 75, 4, 8, 8),
(33, 'none', 10, 1500, 'https://media.travelbay.pl/images/europa/hiszpania/kordoba/kordoba-2.jpg?tr=n-open_graph', 'Prywatna, szeroka i malownicza piaszczysta plaża bezpośrednio przy hotelu z łagodnym dojściem do morza. Przy brzegu rafa koralowa (zalecane obuwie ochronne). Leżaki, materace, parasole i ręczniki dostępne bezpłatnie.', 2, 38, 1, 8, 2),
(34, 'breakfastDinner', 4, 123.9, 'https://i.wpimg.pl/1200x/i.wp.pl/a/f/jpeg/35742/zamosc900_nightman1965_shutterstock.jpeg', 'sratatatatata', 5, 54, 2, 2, 0),
(36, 'breakfastDinner', 1, 129.99, NULL, 'morze plaża rafy koralowe opalanie sie', 3, 68, 5, 4, 1),
(37, 'breakfastSupper', 1, 120, NULL, 'morze plaża zimne drinki opalanie rafy koralowe', 4, 78, 2, 6, 2),
(40, 'none', 2, 0, NULL, 'jjjjyrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrjjjjjjjjjjjjjjjjjjjj', 3, 22, 4, 4, 0.2),
(41, 'none', 3, 0, NULL, 'jjjjyrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrjjjjjjjjjjjjjjjjjjjj', 3, 22, 4, 4, 0.2),
(42, 'none', 24, 0, NULL, 'jjjjyrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrjjjjjjjjjjjjjjjjjjjj', 3, 22, 4, 4, 0.2),
(43, 'none', 2, 0, NULL, 'jjjjyrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrjjjjjjjjjjjjjjjjjjjj', 3, 22, 4, 4, 0.2),
(44, 'none', 2, 0, NULL, 'jjjjyrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrjjjjjjjjjjjjjjjjjjjj', 3, 22, 4, 4, 0.2),
(46, 'none', 2, 0, NULL, 'jjjjyrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrjjjjjjjjjjjjjjjjjjjj', 3, 22, 4, 4, 0.2),
(47, 'none', 2, 0, NULL, 'jjjjyrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrjjjjjjjjjjjjjjjjjjjj', 3, 22, 4, 4, 0.2),
(48, 'none', 2, 0, NULL, 'jjjjyrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrjjjjjjjjjjjjjjjjjjjj', 3, 22, 4, 4, 0.2),
(49, 'supper', 1, 0, NULL, 'rfrgtggggggggggggggggggggggggggggggg', 2, 16, 2, 3, 0.1),
(51, 'breakfast', 4, 5697.99, 'https://1.bp.blogspot.com/-O5dxPSw-z5I/T56EjjoMYnI/AAAAAAAAHr0/XNInjmUb1L4/s1600/100_1187.JPG', 'asddferrrrrrrffffffffffff', 2, 7, 4, 1, 0.2),
(53, 'dinner', 4, 0, 'https://1.bp.blogspot.com/-O5dxPSw-z5I/T56EjjoMYnI/AAAAAAAAHr0/XNInjmUb1L4/s1600/100_1187.JPG', 'rhfjnekmlsaaaaaaaa', 2, 11, 2, 2, 0.3),
(54, 'supper', 2, 269.99, 'https://1.bp.blogspot.com/-O5dxPSw-z5I/T56EjjoMYnI/AAAAAAAAHr0/XNInjmUb1L4/s1600/100_1187.JPG', 'kikiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii', 1, 20, 2, 4, 0.4),
(56, 'breakfast', 4, 5359.99, 'https://1.bp.blogspot.com/-O5dxPSw-z5I/T56EjjoMYnI/AAAAAAAAHr0/XNInjmUb1L4/s1600/100_1187.JPG', 'kjhgfdghjkkljhjuhytg', 2, 13, 3, 3, 0.5),
(58, 'breakfastDinner', 4, 5009.99, 'https://ecowater.pl/assets/BlogsImages/AdobeStock_413608281__FillMaxWzc1MCw0MjJd.jpeg', 'pcnfndfvgbhjkm ncdrtrgh', 3, 25, 3, 4, 1.6),
(59, 'dinner', 2, 2729.99, 'https://zdrojowahotels.pl/media/639c68d18a86253dbf2b9ae2', 'pppppppppppppppppppppppppppppppppppppppppppppppppppp', 2, 16, 2, 3, 0.3);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `types_of_rooms`
--

CREATE TABLE `types_of_rooms` (
  `id_type_of_room` int(11) NOT NULL,
  `room_price` double NOT NULL,
  `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `types_of_rooms`
--

INSERT INTO `types_of_rooms` (`id_type_of_room`, `room_price`, `type`) VALUES
(1, 180, 'singleRoom'),
(2, 360, 'twoPersonRoom'),
(3, 360, 'roomWithTwoSingleBeds'),
(4, 540, 'tripleRoom'),
(5, 950, 'apartament');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `type_of_trip`
--

CREATE TABLE `type_of_trip` (
  `id_type_of_trip` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `type_of_trip`
--

INSERT INTO `type_of_trip` (`id_type_of_trip`, `name`) VALUES
(1, 'lastMinute'),
(2, 'promotions'),
(3, 'exotics'),
(4, 'cruises'),
(5, 'allInclusive'),
(6, 'longTrips'),
(7, 'shortTrips'),
(8, 'familyTrips');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(9) DEFAULT NULL,
  `secret2fa` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `street_number` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `using2fa` bit(1) NOT NULL,
  `zip_code` varchar(5) DEFAULT NULL,
  `activity` bit(1) NOT NULL,
  `provider` varchar(255) NOT NULL,
  `verification_register_code` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `city`, `email`, `firstname`, `lastname`, `password`, `phone_number`, `secret2fa`, `street`, `street_number`, `username`, `using2fa`, `zip_code`, `activity`, `provider`, `verification_register_code`) VALUES
(5, 'Górno', 'this@wp.pl', 'Aleksandra', 'Kwiatkowska', '$2a$10$mtdpA.D8bsaQ5zGLPypgPep9E1b8vEFLa5uDFrOmeSxzj4joosZaW', '732828529', '5C6E3PLLV7IAKQEH', 'Wola Jachowa', '158A', 'ola123', b'1', '26008', b'1', 'LOCAL', NULL),
(6, NULL, 'akwiatkowska7.00@wp.pl', 'Aleksandra', 'Kwiatkowska', '$2a$10$Kq2k1sPdyxPF/S0aBs9dQOiip9QCoSanmtg/6tTNKL0FzWFCHOPaG', NULL, NULL, NULL, NULL, 'user', b'0', NULL, b'1', 'LOCAL', NULL),
(7, 'Górno', 'akwi6atkowslka.00@wp.pl', 'Aleksandra', 'Kwiatkowska', '$2a$10$gGw6z2MchKwKvcyfiFnGKOyc8w6j7DwxzvZPLik3UDnxG6Ei1jLMu', '732828529', NULL, 'Wola Jachowa', '158A', 'user1', b'0', '26008', b'1', 'LOCAL', NULL),
(8, NULL, 'akwiat3kowska.00@wp.pl', 'Aleksandra', 'Kwiatkowska', '$2a$10$DyWMQ.3C5pGoHfNfZ2Mu5eG.YnUVxeWo90V9Rsc.IUaIfzaLBuqWG', NULL, NULL, NULL, NULL, 'ola1234', b'0', NULL, b'0', 'LOCAL', NULL),
(9, NULL, 'akwi33atkowska.00@wp.pl', 'Aleksandra', NULL, NULL, NULL, NULL, NULL, NULL, 'asskwiatkowska.00@wp.pl', b'0', NULL, b'0', 'GOOGLE', NULL),
(10, NULL, 'akwiatkowska.00@wpd3a.pl', 'Aleksandra', NULL, NULL, NULL, NULL, NULL, NULL, 'akwiatkowska.00@wdp.pl', b'0', NULL, b'0', 'GOOGLE', NULL),
(11, NULL, 'e', 'Aleksandra Kwiatkowska', NULL, NULL, NULL, NULL, NULL, NULL, 'aekwiatkowska.00@wp.pl', b'0', NULL, b'0', 'FACEBOOK', NULL),
(12, NULL, 'akwiatkowska.00@wp.6pl', 'Aleksandra', NULL, NULL, NULL, NULL, NULL, NULL, '6', b'0', NULL, b'0', 'GOOGLE', NULL),
(13, NULL, 'akwiatkowska.00@wp.pcl', 'Aleksandra', NULL, NULL, NULL, NULL, NULL, NULL, 'akwiatkowska.00@wp.dpl', b'0', NULL, b'0', 'GOOGLE', NULL),
(14, NULL, 'akwiatkow2ska.00@wp.pl', 'Aleksandra', NULL, NULL, NULL, NULL, NULL, NULL, 'akwiatkowska.00@2wp.pl', b'0', NULL, b'0', 'GOOGLE', NULL),
(15, NULL, 'akwiatkowska.00@wp2.pl', 'Aleksandra', NULL, NULL, NULL, NULL, NULL, NULL, 'akwiatkowska.00@wp.pl3', b'0', NULL, b'0', 'GOOGLE', NULL),
(17, NULL, 'akwiatkowska.00@wp.pr\\l', 'Aleksandra', NULL, NULL, NULL, NULL, NULL, NULL, 'akwiatkowska.00@wp.pler', b'0', NULL, b'0', 'GOOGLE', NULL),
(18, NULL, 'akwiatkowska.00@wp.pld', 'Aleksandra', NULL, NULL, NULL, NULL, NULL, NULL, 'akwiatkowska.00@wpd.pl', b'0', NULL, b'0', 'GOOGLE', NULL),
(19, 'Kielce', 'akwiatkowska.00@wp.plgt', 'Aleksandra', 'Kwiatkowska', NULL, '999111888', NULL, 'jakas', '11', 'akwiatkowska.00@wdp.pldf', b'0', '12345', b'1', 'GOOGLE', NULL),
(20, 'Górno', 'akwiatkowska.00@wp.pl', 'Aleksandra', 'Kwiatkowska', NULL, '732828529', NULL, 'Wola Jachowa', '158A', 'akwiatkowska.00@wp.pl', b'0', '26008', b'1', 'FACEBOOK', NULL),
(21, NULL, 'kwiatkowska.aleksandra_00@wp.plg', 'Ola', 'Kwiatkowska', '$2a$10$CDbjB/qwIhz/dh4.tJarSe9HUaEcIboidcHCSFK7s6K8.bMPSHjUm', NULL, NULL, NULL, NULL, 'v', b'0', NULL, b'1', 'LOCAL', NULL),
(22, NULL, 'kwiatkowska.aleksandra_00@wp.ple3r', 'Ola', 'Kwiatkowska', '$2a$10$LF//4hrBFRinBIOvY6haxuhYjyOXVAWhoCKPc2hQTgyqq/z.rnKfq', NULL, NULL, NULL, NULL, 'e', b'0', NULL, b'1', 'LOCAL', NULL),
(23, NULL, 'kwiatkowska.aleksandra_00@wp.plxcfg', 'Ola', 'Kwiatkowska', '$2a$10$PcoE.4/g605XYGHrwZ97MuIPPmRdTSN8to4DXO1F1A/6A4wVc6c.6', NULL, NULL, NULL, NULL, 'olcian n', b'0', NULL, b'0', 'LOCAL', NULL),
(24, 'Górno', 'kola89111@gmail.com', 'Aleksandra', 'Kwiatkowska', '$2a$10$lPekAx8g5n1hd1NmgckaVe4AE3DNkO6Qq.9Dhr24bH8etZMSu3Z8q', '732828999', NULL, 'Wola Jachowa', '158A', 'olcia', b'0', '25000', b'1', 'LOCAL', NULL),
(25, 'Kielce', 'pracownik@wp.pl', 'Pracownik', 'Pracownik', '$2a$10$fWhNw8vpO2kbZJPQdj63jeRez5bPJXZod1U3/sPM7zvC0boVY.W9C', '123456789', NULL, 'wola', '12', 'pracownik', b'0', '12345', b'1', 'LOCAL', NULL),
(26, 'Górno', 'marysia12257@wp.pl', 'Aleksandra', 'Kwiatkowska', '$2a$10$GeKMRQpRrVg..MlUrY5mw.x.h8PRSiFF8dKcbYsusLEaVTXvhg8L6', '732828529', NULL, 'Wola Jachowa', '158A', 'marysia', b'0', '26008', b'1', 'LOCAL', NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(15, 1),
(17, 1),
(18, 1),
(19, 1),
(20, 1),
(21, 1),
(22, 1),
(23, 1),
(24, 1),
(25, 2),
(26, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_trip_preferences`
--

CREATE TABLE `user_trip_preferences` (
  `id_user_trip_preferences` bigint(20) NOT NULL,
  `activity_level` double NOT NULL,
  `duration` double NOT NULL,
  `food` double NOT NULL,
  `price_level` double NOT NULL,
  `trip_type` double NOT NULL,
  `id_user` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_trip_preferences`
--

INSERT INTO `user_trip_preferences` (`id_user_trip_preferences`, `activity_level`, `duration`, `food`, `price_level`, `trip_type`, `id_user`) VALUES
(10, 3.5, 4, 5.5, 1, 0.5, 24),
(11, 9.5, 1, 0, 1, 5, 26);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `accommodation`
--
ALTER TABLE `accommodation`
  ADD PRIMARY KEY (`id_accommodation`);

--
-- Indeksy dla tabeli `attractions`
--
ALTER TABLE `attractions`
  ADD PRIMARY KEY (`id_attraction`);

--
-- Indeksy dla tabeli `attractions_own_trip`
--
ALTER TABLE `attractions_own_trip`
  ADD PRIMARY KEY (`id_own_offer`,`id_attraction`),
  ADD KEY `FKkcxi1ilsw9swwtnpi5950fflb` (`id_attraction`);

--
-- Indeksy dla tabeli `attractions_trips`
--
ALTER TABLE `attractions_trips`
  ADD PRIMARY KEY (`id_trip`,`id_attraction`),
  ADD KEY `FKjmvjplgt5uq8scex3hoj7y5jb` (`id_attraction`);

--
-- Indeksy dla tabeli `cities`
--
ALTER TABLE `cities`
  ADD PRIMARY KEY (`id_city`),
  ADD KEY `FK56dpq53vewl5ieinvn4h0el27` (`id_country`);

--
-- Indeksy dla tabeli `countries`
--
ALTER TABLE `countries`
  ADD PRIMARY KEY (`id_country`);

--
-- Indeksy dla tabeli `favorite_trips`
--
ALTER TABLE `favorite_trips`
  ADD PRIMARY KEY (`id_favorite_trip`),
  ADD KEY `FKkvyah0yowesyg4lnpe9m2m9xq` (`id_trip`),
  ADD KEY `FKkeoasrv63np2peogd4v6o5kv8` (`id_user`);

--
-- Indeksy dla tabeli `insurances`
--
ALTER TABLE `insurances`
  ADD PRIMARY KEY (`id_insurance`);

--
-- Indeksy dla tabeli `opinions`
--
ALTER TABLE `opinions`
  ADD PRIMARY KEY (`id_opinion`),
  ADD KEY `FKalt3t6xtdp2gjcpr5cnwdgipm` (`id_trip`),
  ADD KEY `FKa99m6arsm4ywq87y221wjldtu` (`id_user`);

--
-- Indeksy dla tabeli `own_offer`
--
ALTER TABLE `own_offer`
  ADD PRIMARY KEY (`id_own_offer`),
  ADD KEY `FKge2cplcvu0nkt2lfcqiawtdk1` (`id_accommodation`),
  ADD KEY `FKhrrso1rbg87ebh35syg5knpi6` (`id_city`),
  ADD KEY `FKs6idklf2cf7hlao02xhwgs9at` (`id_user`),
  ADD KEY `FKqaes7regdtuvibi7md735oof6` (`id_insurance`);

--
-- Indeksy dla tabeli `own_offer_type_of_room`
--
ALTER TABLE `own_offer_type_of_room`
  ADD PRIMARY KEY (`id_own_offer_type_of_room`),
  ADD KEY `FKad3m1x4l5pkpud27wjiawt0o9` (`id_own_offer`),
  ADD KEY `FKbbqb4p6bf87yj12kub8b8ldp` (`id_type_of_room`);

--
-- Indeksy dla tabeli `photos`
--
ALTER TABLE `photos`
  ADD PRIMARY KEY (`id_photo`),
  ADD KEY `FKf1f9nh7vb6p9wftoa2m3ad1n5` (`id_trip`);

--
-- Indeksy dla tabeli `refreshtoken`
--
ALTER TABLE `refreshtoken`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_or156wbneyk8noo4jstv55ii3` (`token`),
  ADD KEY `FKa652xrdji49m4isx38pp4p80p` (`user_id`);

--
-- Indeksy dla tabeli `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`id_reservation`),
  ADD KEY `FK37b14c4us1pdh63g7f0n3lytd` (`id_trip`),
  ADD KEY `FKdiwd3klqyn3i4kyd5om7a4onv` (`id_user`),
  ADD KEY `FKrf2roh8qw5rm2ghwfr4heuynp` (`id_insurance`);

--
-- Indeksy dla tabeli `reservations_type_of_room`
--
ALTER TABLE `reservations_type_of_room`
  ADD PRIMARY KEY (`id_reservations_type_of_room`),
  ADD KEY `FKnil80t521qc2jmyhorp6g6cp6` (`id_reservation`),
  ADD KEY `FKgfs64kl1u45a41gqlfbdl3v5s` (`id_type_of_room`);

--
-- Indeksy dla tabeli `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id_role`);

--
-- Indeksy dla tabeli `transports`
--
ALTER TABLE `transports`
  ADD PRIMARY KEY (`id_transport`);

--
-- Indeksy dla tabeli `trips`
--
ALTER TABLE `trips`
  ADD PRIMARY KEY (`id_trip`),
  ADD KEY `FKdiiottx52u8ipisqv0cwjp11h` (`id_accommodation`),
  ADD KEY `FKt4sy7vs2wtxp79u43nrmni4t3` (`id_city`),
  ADD KEY `FKeasa9usy6fh0hrsdkeodtignw` (`id_transport`),
  ADD KEY `FKbm00xsj1djo1vwa1plksakodb` (`id_type_of_trip`);

--
-- Indeksy dla tabeli `types_of_rooms`
--
ALTER TABLE `types_of_rooms`
  ADD PRIMARY KEY (`id_type_of_room`);

--
-- Indeksy dla tabeli `type_of_trip`
--
ALTER TABLE `type_of_trip`
  ADD PRIMARY KEY (`id_type_of_trip`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- Indeksy dla tabeli `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`);

--
-- Indeksy dla tabeli `user_trip_preferences`
--
ALTER TABLE `user_trip_preferences`
  ADD PRIMARY KEY (`id_user_trip_preferences`),
  ADD KEY `FKj73ofkwp9k60u6nusremwew8g` (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accommodation`
--
ALTER TABLE `accommodation`
  MODIFY `id_accommodation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `attractions`
--
ALTER TABLE `attractions`
  MODIFY `id_attraction` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `cities`
--
ALTER TABLE `cities`
  MODIFY `id_city` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=82;

--
-- AUTO_INCREMENT for table `countries`
--
ALTER TABLE `countries`
  MODIFY `id_country` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `favorite_trips`
--
ALTER TABLE `favorite_trips`
  MODIFY `id_favorite_trip` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `insurances`
--
ALTER TABLE `insurances`
  MODIFY `id_insurance` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `opinions`
--
ALTER TABLE `opinions`
  MODIFY `id_opinion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `own_offer`
--
ALTER TABLE `own_offer`
  MODIFY `id_own_offer` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `own_offer_type_of_room`
--
ALTER TABLE `own_offer_type_of_room`
  MODIFY `id_own_offer_type_of_room` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `photos`
--
ALTER TABLE `photos`
  MODIFY `id_photo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=82;

--
-- AUTO_INCREMENT for table `reservations`
--
ALTER TABLE `reservations`
  MODIFY `id_reservation` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT for table `reservations_type_of_room`
--
ALTER TABLE `reservations_type_of_room`
  MODIFY `id_reservations_type_of_room` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id_role` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `transports`
--
ALTER TABLE `transports`
  MODIFY `id_transport` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `trips`
--
ALTER TABLE `trips`
  MODIFY `id_trip` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT for table `types_of_rooms`
--
ALTER TABLE `types_of_rooms`
  MODIFY `id_type_of_room` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `type_of_trip`
--
ALTER TABLE `type_of_trip`
  MODIFY `id_type_of_trip` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `user_trip_preferences`
--
ALTER TABLE `user_trip_preferences`
  MODIFY `id_user_trip_preferences` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `attractions_own_trip`
--
ALTER TABLE `attractions_own_trip`
  ADD CONSTRAINT `FKjppbf7bhntvq1drbcfkx55b52` FOREIGN KEY (`id_own_offer`) REFERENCES `own_offer` (`id_own_offer`),
  ADD CONSTRAINT `FKkcxi1ilsw9swwtnpi5950fflb` FOREIGN KEY (`id_attraction`) REFERENCES `attractions` (`id_attraction`);

--
-- Constraints for table `attractions_trips`
--
ALTER TABLE `attractions_trips`
  ADD CONSTRAINT `FKj2umqtao9d2yft51squrqskna` FOREIGN KEY (`id_trip`) REFERENCES `trips` (`id_trip`),
  ADD CONSTRAINT `FKjmvjplgt5uq8scex3hoj7y5jb` FOREIGN KEY (`id_attraction`) REFERENCES `attractions` (`id_attraction`);

--
-- Constraints for table `cities`
--
ALTER TABLE `cities`
  ADD CONSTRAINT `FK56dpq53vewl5ieinvn4h0el27` FOREIGN KEY (`id_country`) REFERENCES `countries` (`id_country`);

--
-- Constraints for table `favorite_trips`
--
ALTER TABLE `favorite_trips`
  ADD CONSTRAINT `FKkeoasrv63np2peogd4v6o5kv8` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKkvyah0yowesyg4lnpe9m2m9xq` FOREIGN KEY (`id_trip`) REFERENCES `trips` (`id_trip`);

--
-- Constraints for table `opinions`
--
ALTER TABLE `opinions`
  ADD CONSTRAINT `FKa99m6arsm4ywq87y221wjldtu` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKalt3t6xtdp2gjcpr5cnwdgipm` FOREIGN KEY (`id_trip`) REFERENCES `trips` (`id_trip`);

--
-- Constraints for table `own_offer`
--
ALTER TABLE `own_offer`
  ADD CONSTRAINT `FKge2cplcvu0nkt2lfcqiawtdk1` FOREIGN KEY (`id_accommodation`) REFERENCES `accommodation` (`id_accommodation`),
  ADD CONSTRAINT `FKhrrso1rbg87ebh35syg5knpi6` FOREIGN KEY (`id_city`) REFERENCES `cities` (`id_city`),
  ADD CONSTRAINT `FKqaes7regdtuvibi7md735oof6` FOREIGN KEY (`id_insurance`) REFERENCES `insurances` (`id_insurance`),
  ADD CONSTRAINT `FKs6idklf2cf7hlao02xhwgs9at` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);

--
-- Constraints for table `own_offer_type_of_room`
--
ALTER TABLE `own_offer_type_of_room`
  ADD CONSTRAINT `FKad3m1x4l5pkpud27wjiawt0o9` FOREIGN KEY (`id_own_offer`) REFERENCES `own_offer` (`id_own_offer`),
  ADD CONSTRAINT `FKbbqb4p6bf87yj12kub8b8ldp` FOREIGN KEY (`id_type_of_room`) REFERENCES `types_of_rooms` (`id_type_of_room`);

--
-- Constraints for table `photos`
--
ALTER TABLE `photos`
  ADD CONSTRAINT `FKf1f9nh7vb6p9wftoa2m3ad1n5` FOREIGN KEY (`id_trip`) REFERENCES `trips` (`id_trip`);

--
-- Constraints for table `refreshtoken`
--
ALTER TABLE `refreshtoken`
  ADD CONSTRAINT `FKa652xrdji49m4isx38pp4p80p` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `FK37b14c4us1pdh63g7f0n3lytd` FOREIGN KEY (`id_trip`) REFERENCES `trips` (`id_trip`),
  ADD CONSTRAINT `FKdiwd3klqyn3i4kyd5om7a4onv` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKrf2roh8qw5rm2ghwfr4heuynp` FOREIGN KEY (`id_insurance`) REFERENCES `insurances` (`id_insurance`);

--
-- Constraints for table `reservations_type_of_room`
--
ALTER TABLE `reservations_type_of_room`
  ADD CONSTRAINT `FKgfs64kl1u45a41gqlfbdl3v5s` FOREIGN KEY (`id_type_of_room`) REFERENCES `types_of_rooms` (`id_type_of_room`),
  ADD CONSTRAINT `FKnil80t521qc2jmyhorp6g6cp6` FOREIGN KEY (`id_reservation`) REFERENCES `reservations` (`id_reservation`);

--
-- Constraints for table `trips`
--
ALTER TABLE `trips`
  ADD CONSTRAINT `FKbm00xsj1djo1vwa1plksakodb` FOREIGN KEY (`id_type_of_trip`) REFERENCES `type_of_trip` (`id_type_of_trip`),
  ADD CONSTRAINT `FKdiiottx52u8ipisqv0cwjp11h` FOREIGN KEY (`id_accommodation`) REFERENCES `accommodation` (`id_accommodation`),
  ADD CONSTRAINT `FKeasa9usy6fh0hrsdkeodtignw` FOREIGN KEY (`id_transport`) REFERENCES `transports` (`id_transport`),
  ADD CONSTRAINT `FKt4sy7vs2wtxp79u43nrmni4t3` FOREIGN KEY (`id_city`) REFERENCES `cities` (`id_city`);

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id_role`),
  ADD CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `user_trip_preferences`
--
ALTER TABLE `user_trip_preferences`
  ADD CONSTRAINT `FKj73ofkwp9k60u6nusremwew8g` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
