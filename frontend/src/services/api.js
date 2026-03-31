const API_URL = "http://localhost:8080";

export const register = async (data) => {
    const res = await fetch(`${API_URL}/auth/register`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    });

    return res.json();
};

export const saveMetadata = async (userId, data) => {
    const res = await fetch(`${API_URL}/auth/metadata/${userId}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    });

    return res.json();
};