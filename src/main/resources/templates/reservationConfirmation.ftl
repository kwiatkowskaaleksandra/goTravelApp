<!DOCTYPE html>
<html lang="en">
<head>
    <title>Hi!</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

    <style>
        .card-title{
            font-family: "Comic Sans MS";
            font-weight: bold;
            font-size: 18px;
            text-align: center;
            color: #4ec3ff;
        }

        .card-text {
            font-family: "Comic Sans MS";
            font-size: 16px;
            margin-top: 5%;
        }

    </style>
</head>

<body>
<div class="card" style="width: 700px; height: 800px; margin-left: 20%; margin-top: 5%">
    <div class="row shadow p-3 mb-5 bg-white rounded">
        <div class="col">
            <div class="card-body mt-5">
                <h5 class="card-title">Hi ${username} !</h5>
                <p class="card-text">Thank you for choosing our company to plan your trip. The reservation has been completed successfully. Departure details:</p>
                <ul class="card-text">
                    <li>Departure date: ${departureDate}</li>
                    <li>Number of days: ${numberOfDays}</li>
                </ul>
                <p class="card-text">The cost of the entire trip is ${price} PLN. You can find more information about the booked tour on your profile.</p>
                <br/>
                <p class="card-text">Greetings, GoTravel team</p>
            </div>
        </div>
    </div>

</div>
</body>
</html>