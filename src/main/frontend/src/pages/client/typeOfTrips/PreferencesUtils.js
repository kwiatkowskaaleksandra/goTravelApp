export const setPreferencesActivityLevel = (activityLvl, setState) => {
    if (activityLvl >= 0 && activityLvl < 2) {
        setState({activityLevelSelectedOption: 'option1'});
    } else if (activityLvl >= 2 && activityLvl < 5) {
        setState({activityLevelSelectedOption: 'option2'});
    } else if (activityLvl >= 5 && activityLvl < 7) {
        setState({activityLevelSelectedOption: 'option3'});
    } else if (activityLvl >= 6 && activityLvl < 9) {
        setState({activityLevelSelectedOption: 'option4'});
    } else if (activityLvl >= 8) {
        setState({activityLevelSelectedOption: 'option5'});
    }
}

export const setPreferencesDuration = (duration, setState) => {
    if (duration >= 1 && duration < 3) {
        setState({durationSelectedOption: 'option1'});
    } else if (duration >= 3 && duration < 6) {
        setState({durationSelectedOption: 'option2'});
    } else if (duration >= 6 && duration < 9) {
        setState({durationSelectedOption: 'option3'});
    } else if (duration >= 9 && duration < 14) {
        setState({durationSelectedOption: 'option4'});
    } else if (duration >= 14) {
        setState({durationSelectedOption: 'option5'});
    }
}

export const setPreferencesPriceLevel = (priceLvl, setState) => {
    if (priceLvl <= 2) {
        setState({priceLevelSelectedOption: 'option1'});
    } else if (priceLvl <= 5) {
        setState({priceLevelSelectedOption: 'option2'});
    } else if (priceLvl <= 6) {
        setState({priceLevelSelectedOption: 'option3'});
    } else if (priceLvl <= 9) {
        setState({priceLevelSelectedOption: 'option4'});
    } else {
        setState({priceLevelSelectedOption: 'option5'});
    }
}

export const setPreferencesTripType = (tripType, setState) => {
    if (tripType < 1) {
        setState({tripTypeSelectedOption: 'option1'});
    } else if (tripType >= 1 && tripType < 3) {
        setState({tripTypeSelectedOption: 'option2'});
    } else if (tripType >= 2 && tripType < 4) {
        setState({tripTypeSelectedOption: 'option3'});
    } else if (tripType >= 3 && tripType < 5) {
        setState({tripTypeSelectedOption: 'option4'});
    } else if (tripType >= 4) {
        setState({tripTypeSelectedOption: 'option5'});
    }
}

export const setPreferencesFood = (food, setState) => {
    if (food <= 0.5) {
        setState({foodSelectedOption: 'option1'});
    } else if (food <= 2) {
        setState({foodSelectedOption: 'option2'});
    } else if (food <= 3.5) {
        setState({foodSelectedOption: 'option3'});
    } else if (food <= 5) {
        setState({foodSelectedOption: 'option4'});
    } else if (food <= 6.5) {
        setState({foodSelectedOption: 'option5'});
    } else if (food <= 8) {
        setState({foodSelectedOption: 'option6'});
    } else if (food <= 9.5) {
        setState({foodSelectedOption: 'option7'});
    } else {
        setState({foodSelectedOption: 'option8'});
    }
}

export const mapOptionToActivityLevel = (option) => {
    const optionToValueMap = {
        option1: 1.0,
        option2: 3.5,
        option3: 5.5,
        option4: 7.5,
        option5: 9.5,
    };

    return optionToValueMap[option] || -1
}

export const mapOptionToPriceLevel = (option) => {
    const optionToValueMap = {
        option1: 1.0,
        option2: 4.0,
        option3: 5.5,
        option4: 7.5,
        option5: 9.5,
    };

    return optionToValueMap[option] || -1
}

export const mapOptionToDuration = (option) => {
    const optionToValueMap = {
        option1: 1.0,
        option2: 4.0,
        option3: 6.0,
        option4: 9.0,
        option5: 14.0,
    };

    return optionToValueMap[option] || -1
}

export const mapOptionToTripType = (option) => {
    const optionToValueMap = {
        option1: 0.5,
        option2: 2.0,
        option3: 3.0,
        option4: 4.0,
        option5: 5.0,
    };

    return optionToValueMap[option] || -1
}

export const mapOptionToFood = (option) => {
    const optionToValueMap = {
        option1: 0,
        option2: 1.0,
        option3: 2.5,
        option4: 4.0,
        option5: 5.5,
        option6: 7.0,
        option7: 8.5,
        option8: 10.0
    };

    return optionToValueMap.hasOwnProperty(option) ? optionToValueMap[option] : -1;
}