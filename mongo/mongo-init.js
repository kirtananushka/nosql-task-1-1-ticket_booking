db = db.getSiblingDB('ticket_booking_db');

db.createUser({
    user: "ticket_app_user",
    pwd: "pwd",
    roles: [
        {
            role: "readWrite",
            db: "ticket_booking_db"
        }
    ]
});
