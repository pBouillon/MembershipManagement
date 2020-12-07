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


def add_member(user_id: int, team_id: int) -> None:
    '''Add the a user to a team

    :param user_id: id of the user to add in the team
    :param team_id: id of the team in which the user will be added
    '''
    payload = {'memberToAddId': user_id}

    requests.post(f'{base_url}/api/teams/{team_id}/members', json=payload,
        headers=default_headers)

    log(f'User of id {user_id} successfully added to the team of id {team_id}')


def create_team() -> int:
    '''Create a new team in the API

    :return: The id of the team remotely created
    :rtype: int
    '''
    payload = {'name': 'team name'}

    log(f'Creating a new team from {payload}')

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

    log(f'Creating a new user from {payload}')

    user_id = post_and_extract_id(f'{base_url}/api/users', payload)

    log(f'User of id {user_id} successfully created')

    return user_id


def delete_member(team_id: int, member_id: int) -> None:
    '''Delete a member from a team

    :param team_id: id of the team in which the member should be removed
    :param member_id: id of the user to be removed
    '''
    requests.delete(f'{base_url}/api/teams/{team_id}/members/{member_id}')
    log(f'User of id {member_id} successfully removed from the team of id {team_id}')


def delete_team(team_id: int) -> None:
    '''Delete a team

    :param team_id: id of the team to delete
    '''
    requests.delete(f'{base_url}/api/teams/{team_id}')
    log(f'Delete the team of id {team_id}')


def delete_user(user_id: int) -> None:
    '''Delete a user

    :param user_id: id of the user to delete
    '''
    requests.delete(f'{base_url}/api/users/{user_id}')
    log(f'Delete the user of id {user_id}')


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
