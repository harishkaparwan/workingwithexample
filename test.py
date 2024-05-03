db.credit_cards.aggregate([
    {
        $addFields: {
            currentYear: { $year: "$$NOW" },
            currentMonth: { $month: "$$NOW" }
        }
    },
    {
        $match: {
            $expr: {
                $or: [
                    { $lt: [ "$expirationYear", "$currentYear" ] },  // Expiry year is less than the current year
                    {
                        $and: [
                            { $eq: [ "$expirationYear", "$currentYear" ] },  // Expiry year is the current year
                            { $lt: [ "$expirationMonth", "$currentMonth" ] }  // Expiry month is less than the current month within the same year
                        ]
                    }
                ]
            }
        }
    }
]);
