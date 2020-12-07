# API requests tool

## Installation

In the `api-requests/` folder, run the following:

- `make install` if you are using Linux or if you have `make` installed
- `pip install -r requirements.txt` if you want to manually download the
  dependencies

## Usage

Several options can be used to customize the way the workflow is executed. To
prompt all of them, use the `--help` option:

```console
~$ python api-requests.py --help      
Usage: api-requests.py [OPTIONS]

Options:
  -b, --baseurl TEXT   Specify the base URL of the API (default is:
                       http://localhost:8080)

  -c, --count INTEGER  Number of time to run the workflow, default is 5
  -h, --halt INTEGER   Seconds to be waited before each run of the workflow,
                       default is 3

  -v, --verbose        Set verbosity to True to display actions in the console
                       on execution

  --help               Show this message and exit.
```

For example, if you would like to run the workflow twice with a long pause, you may do:

```console
~$ python api-requests.py --count 2 --halt 30
```
