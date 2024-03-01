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

        .btn {
            background-color: #4ec3ff;
            border-color: #4ec3ff;
            transition: background-color 0.3s;
            color: white;
            font-weight: bold;
            margin-left: 30%;
            font-family: "Comic Sans MS";
            font-size: 18px;
            border-radius: 25px;
            text-decoration: none;
        }

        .btn:hover {
            background-color: #3273dc;
            border-color: #3273dc;
            color: white;
            font-weight: bold;
            text-decoration: none;
        }
    </style>
</head>

<body>
<div class="card" style="width: 700px; height: 800px; margin-left: 20%; margin-top: 5%">
    <div class="row shadow p-3 mb-5 bg-white rounded">
        <div class="col">
            <div class="card-body mt-5">
                <h5 class="card-title">Hi ${username} !</h5>
                <p class="card-text">Thank you for joining us !</p>
                <p class="card-text">Just one more step...</p>
                <p class="card-text">We are glad you are joining us. Confirm your account by clicking the verification link. </p>

                <a class="btn" style="color: white;   width: 300px; height: 40px; text-align: center; line-height: 2; display: block;" role="button" href=${code}>Confirm your account</a>

                <p class="card-text" style="font-size:12px">Remember, until you activate your account,
                    you will not be able to log in.</p>
            </div>
        </div>
    </div>

</div>
</body>
</html>