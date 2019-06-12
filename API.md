# API document

## Credit

* `{baseurl}/credit/add`
  - Method: `POST`
  - FORM-DATA:
    ```
    "id":"4404420288808088888" # id string
    "data":"bbbc" ## credit data
    ```
  - Return (Json):
    - Example:
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
                "id": 2,
                "uploader": "0x68c0f317204fdc15b63409f79f420b79ec4e3609",
                "type": 0,
                "data": "0x256f0a4f6b08925e9b6dcc392a344d18f7dc0228d9c7cf57b6acb9338b626a4d",
                "time": "1560257591959"
            },
            {
                "id": 3,
                "uploader": "0x68c0f317204fdc15b63409f79f420b79ec4e3609",
                "type": 0,
                "data": "0x256f0a4f6b08925e9b6dcc392a344d18f7dc0228d9c7cf57b6acb9338b626a4d",
                "time": "1560257591959"
            }
        ]
        ```