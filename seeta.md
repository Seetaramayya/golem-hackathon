

```
export PRJ=urn:project:e23f80d5-c909-4e2a-96a7-be0394781355
golem-cloud-cli component add --project $PRJ --component-name shopping-cart target/dist/main.wasm

golem-cloud-cli worker invoke-and-await --component $LST --worker-name test1 --function 'demo:lst/api.{insert}' --arg '"item 1"'  --arg '"item 1.5"'

golem-cloud-cli worker add -P $PRJ --component-name shopping-cart --worker-name shopping-cart-1
  New worker created for component urn:component:1542c4b5-394d-416f-b1ba-20826f1ac890, with name shopping-cart-1.
  Worker URN: urn:worker:1542c4b5-394d-416f-b1ba-20826f1ac890/shopping-cart-1

golem-cloud-cli worker list -P $PRJ --component-name shopping-cart

golem-cloud-cli worker invoke-and-await -P $PRJ \
 --component-name shopping-cart \
 --worker-name shopping-cart-1 \
 --function 'pack:name/api.{initialize-cart}' \
 --arg '"test-user"'
```


### URLS 

- Add User => http://localhost:9006/add-user/${name}
- POST Tweet => http://localhost:9006/{user-id}/post-tweet
- Get Tweets => http://localhost:9006/{user-id}/tweets