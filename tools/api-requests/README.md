# API requests tool

Custom script to be used to fire random requests to the API. The requests will
create and/or delete teams and/or users.

## Installation

In the `api-requests/` folder, run the following:

- `make install` if you are using Linux or if you have `make` installed
- `pip install -r requirements.txt` if you want to manually download the
  dependencies

## Usage

Several options can be used to customize the way the requests are executed. To
prompt all of them, use the `--help` option:

```console
~$ python api-requests.py --help      
Usage: api-requests.py [OPTIONS]

Options:
  -b, --baseurl TEXT  Base URL of the API (default is: http://localhost:8080)
  -s, --sleep FLOAT   Seconds to wait before each action (default is: 1)     
  -v, --verbose       Set verbosity to True to display actions in the console
                      on execution

  --help              Show this message and exit.
```

For example, if you would like to run the operations with a pause of 0.5 second
between each call you may do:

```console
~$ python api-requests.py --sleep 0.5
```
