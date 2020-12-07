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
    global base_url
    base_url = baseurl

    global is_verbose
    is_verbose = verbose

    log('Running the predefined workflow, press Ctrl + C to interrupt')

    for index in range(count):
        try:
            log(f'Running the workflow for the #{index + 1} time')
            
            run_workflow()
            
            if index != count - 1:
                log(f'Halting {halt} seconds')
                time.sleep(halt)
        except KeyboardInterrupt:
            log('Shutting down')
            exit(0)


def log(message: str) -> None:
    '''Log an operation to the console if the verbosity is on

    :param message: message to be logged
    '''
    if not is_verbose:
        return
    
    print(f'[LOG] {message}')


def post_and_extract_id(url: str, payload: dict) -> int:
    '''Post the provided payload on the provided url and return the id from the
    response

    :param url: remote URL to query
    :param payload: JSON payload to post

    :return: The id of the resource remotely created
    :rtype: int
    '''
    r = requests.post(url, json=payload, headers=default_headers)
    return r.json()['id']


def run_workflow() -> None:
    '''Run the predefined workflow
    '''
    # Create a user
    user_id = create_user()

    # Create two teams
    team1_id = create_team()
    team2_id = create_team()

    # Add and then remove the user from the first team
    add_member(user_id, team1_id)
    delete_member(team1_id, user_id)

    # Add the user to the second team
    add_member(user_id, team2_id)

    # Delete the teams and the user
    delete_team(team1_id)
    delete_team(team2_id)
    delete_user(user_id)


if __name__ == '__main__':
    '''Default entrypoint calling the CLI default command
    '''
    launch_requests()
