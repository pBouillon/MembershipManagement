import click
import requests
import time


'''Web API base URL to be used when querying its endpoints'''
base_url = 'http://localhost:8080'


'''Default headers when querying the API'''
default_headers = {
    'accept': 'application/json',
    'Content-type': 'application/json',
}


'''Set program's verbosity. True to display actions on execution'''
is_verbose = False

@click.command()
@click.option('--baseurl', '-b', default=base_url,
    help=f'Specify the base URL of the API (default is: {base_url})')
@click.option('--count', '-c', default=5,
    help='Number of time to run the workflow, default is 5')
@click.option('--halt', '-h', default=2,
    help='Seconds to be waited before each run of the workflow, default is 3')
@click.option('--verbose', '-v', is_flag=True,
    help='Set verbosity to True to display actions in the console on execution')
def launch_requests(baseurl: str, count: int, halt: int, verbose: bool) -> None:
    pass


if __name__ == '__main__':
    '''Default entrypoint calling the CLI default command
    '''
    launch_requests()
