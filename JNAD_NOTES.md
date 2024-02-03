
node -p "require('./package.json').version"
node -p "require('./src/commons/utility/format.js').upper('harish')"

'../micro/src/commons/utility/format.js'

1. CJS, we assigned a function to module.exports, in ESM we use the export default
2. CJS is synchronous and ESM is asynchronous
3. Since CJS is implemented in JavaScript, it's dynamic and therefore this is without issue. However, ESM exports must be statically analyzable and this means they can't be conditionally declared. The export keyword only works at the top level.
4. Another static import statement is import { realpath } from 'fs/promises'. This syntax allows us to pull out a specific named export from a module into a reference by the same name (in this case, realpath). 
