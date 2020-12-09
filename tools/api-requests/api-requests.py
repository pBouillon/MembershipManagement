import click
import random
import requests
import time


'''Web API base URL to be used when querying its endpoints'''
base_url = 'http://localhost:8080'


'''List of all the ids of the created teams'''
created_teams_id = []


'''List of all the ids of the created users'''
created_users_id = []


'''Default headers when querying the API'''
default_headers = {
    'accept': 'application/json',
    'Content-type': 'application/json',
}


'''Set program's verbosity. True to display actions on execution'''
is_verbose = False


def create_team() -> int:
    '''Create a new team in the API

    :return: The id of the team remotely created
    :rtype: int
    '''
    payload = {'name': 'team name'}

    team_id = post_and_extract_id(f'{base_url}/api/teams', payload)

    log(f'Team of id {team_id} successfully created')

    return team_id


def create_user() -> int:
    '''Create a new user in the API

    :return: The id of the user remotely created
    :rtype: int
    '''
    payload = {
        'age': 42,
        'firstname': 'user firstname',
        'name': 'user name'
    }

    user_id = post_and_extract_id(f'{base_url}/api/users', payload)

    log(f'User of id {user_id} successfully created')

    return user_id


def delete_created_user():
    '''Delete a user that the script has created
    '''
    if not created_users_id:
        log('Attempting to delete a user but none are remaining')
        return
        
    user_id = random.choice(created_users_id)
    created_users_id.remove(user_id)

    delete_user(user_id)


def delete_created_team():
    '''Delete a team that the script has created
    '''
    if not created_teams_id:
        log('Attempting to delete a team but none are remaining')
        return
        
    team_id = random.choice(created_teams_id)
    created_teams_id.remove(team_id)

    delete_team(team_id)


def delete_team(team_id: int) -> None:
    '''Delete a team

    :param team_id: id of the team to delete
    '''
    requests.delete(f'{base_url}/api/teams/{team_id}')
    log(f'User of id {team_id} successfully deleted')


def delete_user(user_id: int) -> None:
    '''Delete a user

    :param user_id: id of the user to delete
    '''
    requests.delete(f'{base_url}/api/users/{user_id}')
    log(f'User of id {user_id} successfully deleted')


@click.command()
@click.option('--baseurl', '-b', default=base_url,
    help=f'Base URL of the API (default is: {base_url})')
@click.option('--sleep', '-s', default=1.,
    help='Seconds to wait before each action (default is: 1)')
@click.option('--verbose', '-v', is_flag=True,
    help='Set verbosity to True to display actions in the console on execution')
@click.option('--wait', '-w', default=0.,
    help='Seconds to be awaited before launching the requests')
def launch_requests(baseurl: str, sleep: int, verbose: bool, wait: int) -> None:
    global base_url
    base_url = baseurl

    global is_verbose
    is_verbose = verbose

    random.seed(time.time())

    if wait != 0:
        log(f'Waiting {wait} seconds before launching the requests')
        time.sleep(wait)

    log('Running the predefined workflow, press Ctrl + C to interrupt')

    workflow_action = [
        lambda: created_users_id.append(create_user()),
        lambda: created_teams_id.append(create_team()),
        lambda: delete_created_user(),
        lambda: delete_created_team()
    ]

    while True:
        try:
            random.choice(workflow_action)()
            time.sleep(sleep)
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


if __name__ == '__main__':
    '''Default entrypoint calling the CLI default command
    '''
    launch_requests()
