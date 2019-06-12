# API document

## Credit

* `{baseurl}/credit/add`
  - Description: Add credit infomation of a person
  - Method: `POST`
  - FORM-DATA:
    ```
    "id":"4404420288808088888" # id string
    "data":"bbbc" ## credit data
    ```
  - Return:
    A number(creditId)

* `{baseurl}/credit/get`
  - Description: 
  - Method: `POST`
  - FORM-DATA:
    ```
    "id":"4404420288808088888"
    ```
  - Return (Json):
    ``` JSON
    [
        {
            "id": 1,
            "uploader": "0x68c0f317204fdc15b63409f79f420b79ec4e3609",
            "type": 0,
            "data": "0x256f0a4f6b08925e9b6dcc392a344d18f7dc0228d9c7cf57b6acb9338b626a4d",
            "time": "1560257591959"
        },
        {
            ...
        }
    ]
    ```
  