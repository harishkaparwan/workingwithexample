The @azure/msal-react library is a wrapper around the Microsoft Authentication Library (MSAL) for use in React applications. It provides a way to integrate Azure Active Directory (AAD) authentication into your React app. Here's a basic guide on how to use it:

1. Installation
First, you need to install the package. You can do this using npm or yarn:

bash
Copy code
npm install @azure/msal-react @azure/msal-browser
or

bash
Copy code
yarn add @azure/msal-react @azure/msal-browser
2. Configuration
Before using MSAL, you need to set up an AAD application in the Azure portal and obtain your application (client) ID.

Then, you can configure MSAL in your application:

javascript
Copy code
import { PublicClientApplication } from "@azure/msal-browser";
import { MsalProvider } from "@azure/msal-react";

const msalConfig = {
    auth: {
        clientId: "your-client-id", // This is your client ID
        authority: "https://login.microsoftonline.com/common", // This is your authority
        redirectUri: "http://localhost:3000", // This is your redirect URI
    }
};

const msalInstance = new PublicClientApplication(msalConfig);

function App() {
    return (
        <MsalProvider instance={msalInstance}>
            {/* Your app components go here */}
        </MsalProvider>
    );
}

export default App;
3. Authentication
You can use various hooks and components provided by @azure/msal-react to authenticate users. For example, using the useMsal hook:

javascript
Copy code
import React from "react";
import { useMsal } from "@azure/msal-react";

function ProfileContent() {
    const { instance } = useMsal();

    const handleLogin = () => {
        instance.loginPopup().then((response) => {
            console.log(response);
        }).catch(e => {
            console.error(e);
        });
    };

    return (
        <button onClick={handleLogin}>Sign In</button>
    );
}
4. Accessing User Information
After a successful login, you can access user information:

javascript
Copy code
import { useIsAuthenticated, useMsal } from "@azure/msal-react";

function UserInfo() {
    const isAuthenticated = useIsAuthenticated();
    const { accounts } = useMsal();

    if (isAuthenticated) {
        const user = accounts[0];
        return <div>Welcome, {user.name}</div>;
    }

    return <div>You are not signed in.</div>;
}
5. Securing Components
You can use the AuthenticatedTemplate and UnauthenticatedTemplate components to render content based on the user's authentication status:

javascript
Copy code
import { AuthenticatedTemplate, UnauthenticatedTemplate } from "@azure/msal-react";

function AppContent() {
    return (
        <>
            <AuthenticatedTemplate>
                {/* Content to show when user is authenticated */}
            </AuthenticatedTemplate>
            <UnauthenticatedTemplate>
                {/* Content to show when user is not authenticated */}
            </UnauthenticatedTemplate>
        </>
    );
}
6. Handling Tokens
For accessing protected resources, you'll need to acquire tokens. This can be done using the acquireTokenSilent method from MSAL instance, and handling token renewal and refresh scenarios.

Remember to handle errors and edge cases, such as users not consenting to permissions or network issues.

Conclusion
@azure/msal-react simplifies integrating Azure AD authentication into React applications. It provides hooks and components that manage authentication states and user information, allowing you to focus more on your application's functionality. For more advanced scenarios, refer to the official documentation.
